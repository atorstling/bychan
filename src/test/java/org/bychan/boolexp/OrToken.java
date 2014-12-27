package org.bychan.boolexp;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OrToken implements Token<BooleanExpressionNode> {
    @NotNull
    private final LexingMatch match;

    public OrToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Override
    public boolean supportsPrefixParsing() {
        return false;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@Nullable BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean supportsInfixParsing() {
        return true;
    }

    @NotNull
    public BooleanExpressionNode infixParse(@Nullable BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new OrNode(previous, parser.parseExpression(previous, leftBindingPower()));
    }

    public int leftBindingPower() {
        return 20;
    }


    @Override
    @NotNull
    public TokenType<BooleanExpressionNode> getType() {
        return OrTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
