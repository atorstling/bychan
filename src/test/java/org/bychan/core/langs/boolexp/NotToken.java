package org.bychan.core.langs.boolexp;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

public class NotToken implements Token<BooleanExpressionNode> {

    private static final NotToken INSTANCE = new NotToken();

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

    public static NotToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
