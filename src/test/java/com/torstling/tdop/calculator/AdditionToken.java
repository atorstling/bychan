package com.torstling.tdop.calculator;


import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdditionToken<S> implements Token<CalculatorNode> {
    @NotNull
    private final LexingMatch match;

    public AdditionToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public CalculatorNode prefixParse(@Nullable CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public CalculatorNode infixParse(@Nullable CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        CalculatorNode right = parser.tryParse(previous, new ExpressionStatement<>(infixBindingPower())).getRootNode();
        return new AdditionNode(previous, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    public String toString() {
        return "+";
    }

    @Override
    @NotNull
    public TokenType<CalculatorNode> getType() {
        return AdditionTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
