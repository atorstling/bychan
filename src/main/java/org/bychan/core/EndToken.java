package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A token signaling the end of the lexing stream
 *
 * @param <N>
 */
public class EndToken<N> implements Token<N> {


    private final LexingMatch lexingMatch;

    public EndToken(@NotNull final LexingMatch lexingMatch) {
        this.lexingMatch = lexingMatch;
    }

    @Override
    public boolean supportsPrefixParsing() {
        return false;
    }

    @NotNull
    public N prefixParse(@Nullable N previous, @NotNull final TokenParserCallback<N> parser) {
        throw new ParsingFailedException("Cannot parse subExpression, end reached", parser);
    }

    @Override
    public boolean supportsInfixParsing() {
        return false;
    }

    @NotNull
    public N infixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
        throw new ParsingFailedException("Cannot parse subExpression, end reached", parser);
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

        if (lexingMatch != null ? !lexingMatch.equals(endToken.lexingMatch) : endToken.lexingMatch != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return lexingMatch != null ? lexingMatch.hashCode() : 0;
    }
}
