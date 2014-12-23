package org.bychan.boolexp;

import org.bychan.core.LexingMatch;
import org.bychan.core.Token;
import org.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class VariableTokenType<S> implements TokenType<BooleanExpressionNode> {

    private static final VariableTokenType INSTANCE = new VariableTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return VariableToken.<S>valueOf(match);
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
    public static <S> VariableTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
