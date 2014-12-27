package org.bychan.calculator.manual;

import org.bychan.calculator.nodes.CalculatorNode;
import org.bychan.calculator.nodes.SubtractionNode;
import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SubtractionToken<S> implements Token<CalculatorNode> {
    @NotNull
    private final LexingMatch match;

    public SubtractionToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Override
    public boolean supportsPrefixParsing() {
        return false;
    }

    @NotNull
    public CalculatorNode prefixParse(@Nullable CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean supportsInfixParsing() {
        return true;
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }

    @NotNull
    public CalculatorNode infixParse(@Nullable CalculatorNode previous, @NotNull TokenParserCallback<CalculatorNode> parser) {
        CalculatorNode right = parser.parseExpression(previous, leftBindingPower());
        return new SubtractionNode(previous, right);
    }

    public int leftBindingPower() {
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
