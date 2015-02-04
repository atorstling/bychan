package org.bychan.core.langs.shared;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

public class LeftParenthesisToken<N> implements Token<N> {

    @NotNull
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        return new LeftParenthesisLexeme<>(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("\\(");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public static <N> LeftParenthesisToken<N> get() {
        return new LeftParenthesisToken<>();
    }
}
