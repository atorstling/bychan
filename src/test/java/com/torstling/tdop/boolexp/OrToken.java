package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class OrToken implements Token<BooleanExpressionNode> {
    @NotNull
    private final LexingMatch match;

    public OrToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull BooleanExpressionNode parent, @NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public BooleanExpressionNode infixParse(BooleanExpressionNode parent, @NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new OrNode(left, parser.expression(parent, infixBindingPower()));
    }

    public int infixBindingPower() {
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
