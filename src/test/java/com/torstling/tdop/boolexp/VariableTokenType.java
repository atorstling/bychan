package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class VariableTokenType implements TokenType<BooleanExpressionNode> {

    private static final VariableTokenType INSTANCE = new VariableTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return VariableToken.valueOf(match);
    }

    @NotNull
    public String getPattern() {
        return "[a-z]+";
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    @NotNull
    public static VariableTokenType get() {
        return INSTANCE;
    }
}
