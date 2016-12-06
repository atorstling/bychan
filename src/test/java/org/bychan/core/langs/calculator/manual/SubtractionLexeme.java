package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.*;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.calculator.nodes.SubtractionNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SubtractionLexeme implements Lexeme<CalculatorNode> {
    @NotNull
    private final LexingMatch match;

    public SubtractionLexeme(@NotNull final LexingMatch match) {
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
            return new SubtractionNode(left, right);
        };
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }

    public int lbp() {
        return 10;
    }

    public String toString() {
        return "-";
    }


    @Override
    @NotNull
    public Token<CalculatorNode> getToken() {
        return SubtractionToken.get();
    }

}
