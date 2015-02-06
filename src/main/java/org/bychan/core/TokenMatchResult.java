package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alext on 2015-02-06.
 *
 * @param <M> type of object which a lexerResult results in
 */
public class TokenMatchResult<M> {
    @Nullable
    private final M lexerResult;
    private final int endPosition;

    private TokenMatchResult(@Nullable final M lexerResult, int endPosition) {
        this.lexerResult = lexerResult;
        this.endPosition = endPosition;
    }

    @NotNull
    public static <M> TokenMatchResult<M> create(int endPosition) {
        return new TokenMatchResult<>(null, endPosition);
    }

    @NotNull
    public static <M> TokenMatchResult<M> create(@NotNull final M lexerResult, int endPosition) {
        return new TokenMatchResult<>(lexerResult, endPosition);
    }

    @Nullable
    public M getLexerResult() {
        return lexerResult;
    }

    public int getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenMatchResult that = (TokenMatchResult) o;

        return endPosition == that.endPosition && !(lexerResult != null ? !lexerResult.equals(that.lexerResult) :
                that.lexerResult != null);
    }

    @Override
    public int hashCode() {
        int result = lexerResult != null ? lexerResult.hashCode() : 0;
        result = 31 * result + endPosition;
        return result;
    }
}
