package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A token signaling the end of the lexing stream
 *
 *
 */
public class EndLexeme<N> implements Lexeme<N> {
    @NotNull
    private final LexingMatch lexingMatch;

    public EndLexeme(@NotNull final LexingMatch lexingMatch) {
        this.lexingMatch = lexingMatch;
    }


    @Nullable
    @Override
    public NudParseAction<N> getNud() {
        return (left, parser) -> {
            throw ParsingFailedException.forFailedAfterLexing("Premature end reached", parser);
        };
    }

    @Nullable
    @Override
    public LedParseAction<N> getLed() {
        return null;
    }

    public int leftBindingPower() {
        return 0;
    }


    @Override
    @NotNull
    public Token<N> getToken() {
        return EndToken.get();
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

        EndLexeme endToken = (EndLexeme) o;

        return lexingMatch.equals(endToken.lexingMatch);

    }

    @Override
    public int hashCode() {
        return lexingMatch.hashCode();
    }
}
