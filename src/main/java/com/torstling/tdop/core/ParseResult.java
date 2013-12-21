package com.torstling.tdop.core;

import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * The result of a possibly unsuccessful parse
 */
public class ParseResult<N extends AstNode> {
    @Nullable
    private final N node;
    @Nullable
    private final String errorMessage;

    private ParseResult(@Nullable final N node, @Nullable final String errorMessage) {
        this.node = node;
        this.errorMessage = errorMessage;
    }

    @NotNull
    public static <N extends AstNode> ParseResult<N> success(@NotNull final N node) {
        return new ParseResult<>(node, null);
    }

    @NotNull
    public static <N extends AstNode> ParseResult<N> failure(@NotNull final String errorMessage) {
        return new ParseResult<>(null, errorMessage);
    }

    public boolean isSuccess() {
        return node != null;
    }

    @NotNull
    public N getNode() {
        if (!isSuccess()) {
            throw new IllegalStateException("Cannot get node when parsing wasn't successful. Failure:" + errorMessage);
        }
        return node;
    }

    @NotNull
    public String getErrorMessage() {
        if (!isFailure()) {
            throw new IllegalStateException("Cannot fetch error message when parsing was successful.");
        }
        return errorMessage;
    }

    public boolean isFailure() {
        return !isSuccess();
    }
}
