package org.bychan.core.dynamic;

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
    private final int matchEndPosition;

    private TokenMatchResult(@Nullable final M lexerValue, int matchEndPosition) {
        this.lexerValue = lexerValue;
        this.matchEndPosition = matchEndPosition;
    }

    @NotNull
    public static <M> TokenMatchResult<M> create(int matchEndPosition) {
        return new TokenMatchResult<>(null, matchEndPosition);
    }

    @NotNull
    public static <M> TokenMatchResult<M> create(@NotNull final M lexerValue, int matchEndPosition) {
        return new TokenMatchResult<>(lexerValue, matchEndPosition);
    }

    @Nullable
    public M getLexerValue() {
        return lexerValue;
    }

    public int getMatchEndPosition() {
        return matchEndPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenMatchResult that = (TokenMatchResult) o;

        return matchEndPosition == that.matchEndPosition && !(lexerValue != null ? !lexerValue.equals(that.lexerValue) :
                that.lexerValue != null);
    }

    @Override
    public int hashCode() {
        int result = lexerValue != null ? lexerValue.hashCode() : 0;
        result = 31 * result + matchEndPosition;
        return result;
    }
}
