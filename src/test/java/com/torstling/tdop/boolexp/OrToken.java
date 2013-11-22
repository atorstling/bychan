package com.torstling.tdop.boolexp;

import com.torstling.tdop.LexingMatch;
import com.torstling.tdop.Token;
import com.torstling.tdop.TokenParserCallback;
import com.torstling.tdop.TokenType;
import org.jetbrains.annotations.NotNull;

public class OrToken implements Token<BooleanExpressionNode> {
    @NotNull
    private final LexingMatch match;

    public OrToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new OrNode(left, parser.expression(infixBindingPower()));
    }

    public int infixBindingPower() {
        return 20;
    }

    @Override
    public boolean isOfType(@NotNull TokenType<BooleanExpressionNode> type) {
        return type.equals(OrTokenType.get());
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
