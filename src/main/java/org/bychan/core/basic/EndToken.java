package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A token signaling the end of the lexing stream
 *
 * @param <N>
 */
public class EndToken<N> implements Token<N> {
    @NotNull
    private final LexingMatch lexingMatch;

    public EndToken(@NotNull final LexingMatch lexingMatch) {
        this.lexingMatch = lexingMatch;
    }


    @Nullable
    @Override
    public PrefixParseAction<N> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public InfixParseAction<N> getInfixParser() {
        return null;
    }

    public int leftBindingPower() {
        return 0;
    }


    @Override
    @NotNull
    public TokenType<N> getType() {
        return EndTokenType.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return lexingMatch;
    }

    public String toString() {
        return "END";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndToken endToken = (EndToken) o;

        return lexingMatch.equals(endToken.lexingMatch);

    }

    @Override
    public int hashCode() {
        return lexingMatch.hashCode();
    }
}
