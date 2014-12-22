package com.torstling.bychan.calculator.manual;

import com.torstling.bychan.calculator.nodes.CalculatorNode;
import com.torstling.bychan.calculator.nodes.NumberNode;
import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenParserCallback;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NumberToken<S> implements Token<CalculatorNode> {
    @NotNull
    private final LexingMatch match;


    @Override
    @NotNull
    public TokenType<CalculatorNode> getType() {
        return NumberTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }

    private final int value;

    public NumberToken(@NotNull LexingMatch match) {
        this.match = match;
        this.value = Integer.parseInt(match.getText());
    }

    public static <S> Token<CalculatorNode> valueOf(@NotNull final LexingMatch match) {
        return new NumberToken<>(match);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberToken that = (NumberToken) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }


    @NotNull
    public CalculatorNode prefixParse(@Nullable CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        return new NumberNode(value);
    }

    @NotNull
    public CalculatorNode infixParse(@Nullable CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.valueOf(value);
    }

}
