package com.torstling.tdop.calculator;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class NumberToken<S> implements Token<CalculatorNode, S> {
    @NotNull
    private final LexingMatch match;


    @Override
    @NotNull
    public TokenType<CalculatorNode, S> getType() {
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

    public static <S> Token<CalculatorNode, S> valueOf(@NotNull final LexingMatch match) {
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
    public CalculatorNode prefixParse(@NotNull S parent, @NotNull TokenParserCallback<CalculatorNode, S> parser) {
        return new NumberNode(value);
    }

    @NotNull
    public CalculatorNode infixParse(S parent, @NotNull CalculatorNode left, @NotNull TokenParserCallback<CalculatorNode, S> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.valueOf(value);
    }

}
