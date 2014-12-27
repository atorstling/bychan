package org.bychan.boolexp;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AndToken<S> implements Token<BooleanExpressionNode> {
    private final LexingMatch match;

    public AndToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Override
    public boolean supportsPrefixParsing() {
        return false;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@Nullable BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        throw new UnsupportedOperationException("'And' operator cannot be used as a prefix");
    }

    @Override
    public boolean supportsInfixParsing() {
        return true;
    }

    @NotNull
    public BooleanExpressionNode infixParse(@Nullable BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        BooleanExpressionNode right = parser.parseExpression(previous, leftBindingPower());
        return new AndNode(previous, right);
    }

    public int leftBindingPower() {
        return 10;
    }

    @Override
    @NotNull
    public TokenType<BooleanExpressionNode> getType() {
        return AndTokenType.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}

