package com.torstling.tdop.core;

import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * The result of a possibly unsuccessful parse
 */
public class ParseResult<N> {
    @Nullable
    private final N rootNode;
    @Nullable
    private final ParsingFailedInformation errorMessage;

    private ParseResult(@Nullable final N rootNode, @Nullable final ParsingFailedInformation errorMessage) {
        this.rootNode = rootNode;
        this.errorMessage = errorMessage;
    }

    @NotNull
    public static <N> ParseResult<N> success(@NotNull final N rootNode) {
        return new ParseResult<>(rootNode, null);
    }

    @NotNull
    public static <N> ParseResult<N> failure(@NotNull final ParsingFailedInformation errorMessage) {
        return new ParseResult<>(null, errorMessage);
    }

    public boolean isSuccess() {
        return rootNode != null;
    }

    @NotNull
    public N getRootNode() {
        if (!isSuccess()) {
            throw new ParsingFailedException(errorMessage);
        }
        return rootNode;
    }

    @NotNull
    public ParsingFailedInformation getErrorMessage() {
        if (!isFailure()) {
            throw new IllegalStateException("Cannot fetch error message when parsing was successful.");
        }
        return errorMessage;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    @Override
    public String toString() {
        return "ParseResult{" +
                "rootNode=" + rootNode +
                ", errorMessage=" + errorMessage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParseResult that = (ParseResult) o;

        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        if (rootNode != null ? !rootNode.equals(that.rootNode) : that.rootNode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rootNode != null ? rootNode.hashCode() : 0;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        return result;
    }
}
