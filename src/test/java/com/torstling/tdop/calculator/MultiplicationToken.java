package com.torstling.tdop.calculator;


import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class MultiplicationToken<S> implements Token<CalculatorNode, S> {

    @NotNull
    private final LexingMatch match;

    public MultiplicationToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public CalculatorNode prefixParse(@NotNull S parent, @NotNull TokenParserCallback<CalculatorNode, S> parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public CalculatorNode infixParse(S parent, @NotNull CalculatorNode left, @NotNull TokenParserCallback<CalculatorNode, S> parser) {
        CalculatorNode right = parser.tryParse(new ExpressionParserStrategy<CalculatorNode, S>(parent, infixBindingPower())).getRootNode();
        return new MultiplicationNode(left, right);
    }

    public int infixBindingPower() {
        return 20;
    }

    public String toString() {
        return "*";
    }


    @Override
    @NotNull
    public TokenType<CalculatorNode, S> getType() {
        return MultiplicationTokenType.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
