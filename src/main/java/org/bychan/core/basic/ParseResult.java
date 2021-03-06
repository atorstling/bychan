package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The result of a possibly unsuccessful parse
 */
public class ParseResult<N> {
    @Nullable
    private final N rootNode;
    @Nullable
    private final FailureInformation errorMessage;

    private ParseResult(@Nullable final N rootNode, @Nullable final FailureInformation errorMessage) {
        this.rootNode = rootNode;
        this.errorMessage = errorMessage;
    }

    @NotNull
    public static <N> ParseResult<N> success(@NotNull final N rootNode) {
        return new ParseResult<>(rootNode, null);
    }

    @NotNull
    public static <N> ParseResult<N> failure(@NotNull final FailureInformation errorMessage) {
        return new ParseResult<>(null, errorMessage);
    }

    public boolean isSuccess() {
        return rootNode != null;
    }

    @NotNull
    public N root() {
        checkSuccess();
        assert rootNode != null;
        return rootNode;
    }

    @NotNull
    public FailureInformation getErrorMessage() {
        if (!isFailure()) {
            throw new IllegalStateException("Cannot fetch error message when parsing was successful.");
        }
        assert errorMessage != null;
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

        return !(errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) && !(rootNode != null ? !rootNode.equals(that.rootNode) : that.rootNode != null);

    }

    @Override
    public int hashCode() {
        int result = rootNode != null ? rootNode.hashCode() : 0;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        return result;
    }

    public void checkSuccess() {
        if (!isSuccess()) {
            assert errorMessage != null;
            throw new ParsingFailedException(errorMessage);
        }
    }
}
