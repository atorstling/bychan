package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class VariableToken implements Token<BooleanExpressionNode> {

    private static final VariableToken INSTANCE = new VariableToken();

    @NotNull
    public Lexeme<BooleanExpressionNode> toLexeme(@NotNull LexingMatch match) {
        return VariableLexeme.valueOf(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("[a-z]+");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    @NotNull
    public static VariableToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
