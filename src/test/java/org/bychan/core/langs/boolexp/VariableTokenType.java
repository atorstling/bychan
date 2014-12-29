package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class VariableTokenType implements TokenType<BooleanExpressionNode> {

    private static final VariableTokenType INSTANCE = new VariableTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return VariableToken.valueOf(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("[a-z]+");
    }

    @Override
    public boolean include() {
        return true;
    }

    @NotNull
    public static  VariableTokenType get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
