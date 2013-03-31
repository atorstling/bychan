package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class NotToken implements Token<BooleanExpressionNode> {

    @NotNull
    private final LexingMatch match;

    public NotToken(LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new NotNode(parser.expression(infixBindingPower()));
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 1;
    }

    @Override
    public boolean isOfType(@NotNull final TokenType<BooleanExpressionNode> type) {
        return type.equals(NotTokenType.get());
    }

    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
