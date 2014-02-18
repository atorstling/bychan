package com.torstling.tdop.calculator;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class SubtractionToken<S> implements Token<CalculatorNode> {
    @NotNull
    private final LexingMatch match;

    public SubtractionToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public CalculatorNode prefixParse(@NotNull CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }

    @NotNull
    public CalculatorNode infixParse(@NotNull CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        CalculatorNode right = parser.tryParse(new ExpressionParserStrategy<>(previous, infixBindingPower())).getRootNode();
        return new SubtractionNode(previous, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    public String toString() {
        return "-";
    }


    @Override
    @NotNull
    public TokenType<CalculatorNode> getType() {
        return SubtractionTokenType.get();
    }

}
