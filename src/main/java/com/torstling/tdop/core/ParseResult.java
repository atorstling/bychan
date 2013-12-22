package com.torstling.tdop.core;

import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * The result of a possibly unsuccessful parse
 */
public class ParseResult<N extends AstNode> {
    @Nullable
    private final N rootNode;
    @Nullable
    private final String errorMessage;

    private ParseResult(@Nullable final N rootNode, @Nullable final String errorMessage) {
        this.rootNode = rootNode;
        this.errorMessage = errorMessage;
    }

    @NotNull
    public static <N extends AstNode> ParseResult<N> success(@NotNull final N rootNode) {
        return new ParseResult<>(rootNode, null);
    }

    @NotNull
    public static <N extends AstNode> ParseResult<N> failure(@NotNull final String errorMessage) {
        return new ParseResult<>(null, errorMessage);
    }

    public boolean isSuccess() {
        return rootNode != null;
    }

    @NotNull
    public N getRootNode() {
        if (!isSuccess()) {
            throw new IllegalStateException("Cannot get rootNode when parsing wasn't successful. Failure:" + errorMessage);
        }
        return rootNode;
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
