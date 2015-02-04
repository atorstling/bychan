package org.bychan.core.langs.shared;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

public class WhitespaceToken<N> implements Token<N> {
    private final String pattern;

    public WhitespaceToken() {
        this.pattern = "\\s+";
    }

    @NotNull
    @Override
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        return new WhitespaceLexeme<>(this, match);
    }

    @NotNull
    @Override
    public TokenMatcher getMatcher() {
        return new RegexMatcher(pattern);
    }

    @Override
    public boolean keepAfterLexing() {
        return false;
    }
}
