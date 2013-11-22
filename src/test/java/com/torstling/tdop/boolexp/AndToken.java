package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class AndToken implements Token<BooleanExpressionNode> {
    private final LexingMatch match;

    public AndToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException("'And' operator cannot be used as a prefix");
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        BooleanExpressionNode right = parser.expression(infixBindingPower());
        return new AndNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    @Override
    public boolean isOfType(@NotNull final TokenType<BooleanExpressionNode> type) {
        return type.equals(AndTokenType.get());
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}

