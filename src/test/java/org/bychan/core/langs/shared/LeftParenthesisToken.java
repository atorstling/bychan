package org.bychan.core.langs.shared;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.dynamic.RegexMatcher;
import org.bychan.core.dynamic.TokenMatcher;
import org.jetbrains.annotations.NotNull;

public class LeftParenthesisToken<N> implements Token<N> {

    public static <N> LeftParenthesisToken<N> get() {
        return new LeftParenthesisToken<>();
    }

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
}
