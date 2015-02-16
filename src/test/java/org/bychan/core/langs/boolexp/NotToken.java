package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.dynamic.RegexMatcher;
import org.bychan.core.dynamic.TokenMatcher;
import org.jetbrains.annotations.NotNull;

public class NotToken implements Token<BooleanExpressionNode> {

    private static final NotToken INSTANCE = new NotToken();

    public static NotToken get() {
        //noinspection unchecked
        return INSTANCE;
    }

    @NotNull
    public Lexeme<BooleanExpressionNode> toLexeme(@NotNull LexingMatch match) {
        return new NotLexeme(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("!");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }
}
