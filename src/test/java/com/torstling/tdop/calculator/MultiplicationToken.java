package com.torstling.tdop.calculator;


import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class MultiplicationToken implements Token<CalculatorNode> {

    @NotNull
    private final LexingMatch match;

    public MultiplicationToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public CalculatorNode infixParse(@NotNull CalculatorNode left, @NotNull TokenParserCallback<CalculatorNode> parser) {
        CalculatorNode right = parser.expression(infixBindingPower());
        return new MultiplicationNode(left, right);
    }

    public int infixBindingPower() {
        return 20;
    }

    public String toString() {
        return "*";
    }

    @Override
    public boolean isOfType(@NotNull TokenType<CalculatorNode> type) {
        return type.equals(MultiplicationTokenType.get());
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}