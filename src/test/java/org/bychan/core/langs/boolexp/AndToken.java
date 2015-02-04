package org.bychan.core.langs.boolexp;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

public class AndToken implements Token<BooleanExpressionNode> {

    private static final AndToken INSTANCE = new AndToken();

    @NotNull
    public Lexeme<BooleanExpressionNode> toLexeme(@NotNull LexingMatch match) {
        return new AndLexeme(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("\\*");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    @NotNull
    public static AndToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
