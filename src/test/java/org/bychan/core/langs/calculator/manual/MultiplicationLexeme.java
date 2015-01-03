package org.bychan.core.langs.calculator.manual;


import org.bychan.core.basic.*;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiplicationLexeme implements Lexeme<CalculatorNode> {

    @NotNull
    private final LexingMatch match;

    public MultiplicationLexeme(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public NudParseAction<CalculatorNode> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public LedParseAction<CalculatorNode> getInfixParser() {
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
    public Token<CalculatorNode> getToken() {
        return MultiplicationToken.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
