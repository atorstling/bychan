package org.bychan.core;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alext on 2/28/14.
 */
public class ParsingPosition {
    private final int position;
    @NotNull
    private final List<? extends Token> remainingTokens;

    public ParsingPosition(int position, @NotNull List<? extends Token> remainingTokens) {
        this.position = position;
        this.remainingTokens = remainingTokens;
    }

    public ParsingPosition(int position, Token... tokens) {
        this(position, Arrays.asList(tokens));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        return position == that.position && remainingTokens.equals(that.remainingTokens);

    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + remainingTokens.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " index " + position +
                ", remaining tokens are " + remainingTokens;
    }
}
