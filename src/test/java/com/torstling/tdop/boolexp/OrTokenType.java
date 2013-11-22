package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class OrTokenType implements TokenType<BooleanExpressionNode> {
    private static final OrTokenType INSTANCE = new OrTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new OrToken(match);
    }

    @NotNull
    public String getPattern() {
        return "\\+";
    }

    @NotNull
    public static OrTokenType get() {
        return INSTANCE;
    }
}
