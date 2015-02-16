package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.dynamic.RegexMatcher;
import org.bychan.core.dynamic.TokenMatcher;
import org.jetbrains.annotations.NotNull;

public class VariableToken implements Token<BooleanExpressionNode> {

    private static final VariableToken INSTANCE = new VariableToken();

    @NotNull
    public static VariableToken get() {
        //noinspection unchecked
        return INSTANCE;
    }

    @NotNull
    public Lexeme<BooleanExpressionNode> toLexeme(@NotNull LexingMatch match) {
        return VariableLexeme.valueOf(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("[a-z]+");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }
}
