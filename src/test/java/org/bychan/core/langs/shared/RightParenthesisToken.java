package org.bychan.core.langs.shared;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

public class RightParenthesisToken<N> implements Token<N> {

    private static final RightParenthesisToken INSTANCE = new RightParenthesisToken<>();

    @NotNull
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        return new RightParenthesisLexeme<>(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("\\)");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public static <N> RightParenthesisToken<N> get() {
        //noinspection unchecked
        return (RightParenthesisToken<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
