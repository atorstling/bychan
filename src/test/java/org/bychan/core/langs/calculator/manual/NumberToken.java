package org.bychan.core.langs.calculator.manual;

import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.calculator.nodes.NumberNode;
import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NumberToken implements Token<CalculatorNode> {
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
        return new NumberToken(match);
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

    @Nullable
    @Override
    public PrefixParseAction<CalculatorNode> getPrefixParser() {
        return (previous, parser) -> new NumberNode(value);
    }

    @Nullable
    @Override
    public InfixParseAction<CalculatorNode> getInfixParser() {
        return null;
    }

    public int leftBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.valueOf(value);
    }

}
