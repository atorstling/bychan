package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class OrToken<S> implements Token<BooleanExpressionNode, S> {
    @NotNull
    private final LexingMatch match;

    public OrToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull S parent, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public BooleanExpressionNode infixParse(S parent, @NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        return new OrNode(left, parser.expression(parent, infixBindingPower()));
    }

    public int infixBindingPower() {
        return 20;
    }


    @Override
    @NotNull
    public TokenType<BooleanExpressionNode, S> getType() {
        return OrTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
