package com.torstling.tdop.calculator;


import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class MultiplicationToken<S> implements Token<CalculatorNode> {

    @NotNull
    private final LexingMatch match;

    public MultiplicationToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public CalculatorNode prefixParse(@NotNull CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public CalculatorNode infixParse(@NotNull CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        CalculatorNode right = parser.tryParse(previous, new ExpressionParserStrategy<>(infixBindingPower())).getRootNode();
        return new MultiplicationNode(previous, right);
    }

    public int infixBindingPower() {
        return 20;
    }

    public String toString() {
        return "*";
    }


    @Override
    @NotNull
    public TokenType<CalculatorNode> getType() {
        return MultiplicationTokenType.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
