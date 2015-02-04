package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.*;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.calculator.nodes.NumberNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NumberLexeme implements Lexeme<CalculatorNode> {
    @NotNull
    private final LexingMatch match;


    @Override
    @NotNull
    public Token<CalculatorNode> getToken() {
        return NumberToken.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }

    private final int value;

    public NumberLexeme(@NotNull LexingMatch match) {
        this.match = match;
        this.value = Integer.parseInt(match.getText());
    }

    public static Lexeme<CalculatorNode> valueOf(@NotNull final LexingMatch match) {
        return new NumberLexeme(match);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberLexeme that = (NumberLexeme) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Nullable
    @Override
    public NudParseAction<CalculatorNode> getNud() {
        return (left, parser) -> new NumberNode(value);
    }

    @Nullable
    @Override
    public LedParseAction<CalculatorNode> getLed() {
        return null;
    }

    public int leftBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.valueOf(value);
    }

}
