package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alext on 2015-02-06.
 *
 * @param <M> type of object which a lexerValue results in
 */
public class TokenMatchResult<M> {
    @Nullable
    private final M lexerValue;
    private final int endPosition;

    private TokenMatchResult(@Nullable final M lexerValue, int endPosition) {
        this.lexerValue = lexerValue;
        this.endPosition = endPosition;
    }

    @NotNull
    public static <M> TokenMatchResult<M> create(int endPosition) {
        return new TokenMatchResult<>(null, endPosition);
    }

    @NotNull
    public static <M> TokenMatchResult<M> create(@NotNull final M lexerValue, int endPosition) {
        return new TokenMatchResult<>(lexerValue, endPosition);
    }

    @Nullable
    public M getLexerValue() {
        return lexerValue;
    }

    public int getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenMatchResult that = (TokenMatchResult) o;

        return endPosition == that.endPosition && !(lexerValue != null ? !lexerValue.equals(that.lexerValue) :
                that.lexerValue != null);
    }

    @Override
    public int hashCode() {
        int result = lexerValue != null ? lexerValue.hashCode() : 0;
        result = 31 * result + endPosition;
        return result;
    }
}
