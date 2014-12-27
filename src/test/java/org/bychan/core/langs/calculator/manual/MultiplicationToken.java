package org.bychan.core.langs.calculator.manual;


import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiplicationToken implements Token<CalculatorNode> {

    @NotNull
    private final LexingMatch match;

    public MultiplicationToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public PrefixParseAction<CalculatorNode> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public InfixParseAction<CalculatorNode> getInfixParser() {
        return (previous, parser) -> {
            CalculatorNode right = parser.parseExpression(previous, leftBindingPower());
            return new MultiplicationNode(previous, right);
        };
    }

    public int leftBindingPower() {
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
