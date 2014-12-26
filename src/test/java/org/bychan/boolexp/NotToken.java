package org.bychan.boolexp;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotToken<S> implements Token<BooleanExpressionNode> {

    @NotNull
    private final LexingMatch match;

    public NotToken(@NotNull LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@Nullable BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new NotNode(parser.parseExpression(previous, infixBindingPower()));
    }

    @NotNull
    public BooleanExpressionNode infixParse(@Nullable BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 1;
    }

    @Override
    @NotNull
    public TokenType<BooleanExpressionNode> getType() {
        return NotTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
