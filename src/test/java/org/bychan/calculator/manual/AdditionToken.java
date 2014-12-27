package org.bychan.calculator.manual;


import org.bychan.calculator.nodes.AdditionNode;
import org.bychan.calculator.nodes.CalculatorNode;
import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdditionToken implements Token<CalculatorNode> {
    @NotNull
    private final LexingMatch match;

    public AdditionToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public PrefixParser<CalculatorNode> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public InfixParser<CalculatorNode> getInfixParser() {
        return (previous, parser) -> {
            CalculatorNode right = parser.parseExpression(previous, leftBindingPower());
            return new AdditionNode(previous, right);
        };
    }

    public int leftBindingPower() {
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
