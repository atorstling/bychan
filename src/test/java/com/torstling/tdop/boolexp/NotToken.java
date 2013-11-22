package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
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

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}