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
    public NudParseAction<CalculatorNode> getNud() {
        return null;
    }

    @Nullable
    @Override
    public LedParseAction<CalculatorNode> getLed() {
        return (left, parser) -> {
            CalculatorNode right = parser.expr(left, lbp());
            return new MultiplicationNode(left, right);
        };
    }

    public int lbp() {
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
