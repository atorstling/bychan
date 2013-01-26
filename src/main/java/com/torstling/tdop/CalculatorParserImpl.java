package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.ArrayDeque;
import java.util.List;

public class CalculatorParserImpl implements TokenParserCallback {

    @NotNull
    private final ArrayDeque<Token> tokens;

    public CalculatorParserImpl(List<? extends Token> tokens) {
        this.tokens = new ArrayDeque<Token>(tokens);
    }

    @NotNull
    public Node parse() {
        return expression(0);
    }

    @NotNull
    public Node expression(int callerInfixBindingPower) {
        Token first = tokens.pop();
        Node rootNode = first.prefixParse(this);
        while (callerInfixBindingPower < tokens.peek().infixBindingPower()) {
            Token second = tokens.pop();
            rootNode = second.infixParse(rootNode, this);
        }
        return rootNode;
    }

    public void swallow(Class<? extends Token> expectedClass) {
        Token next = tokens.pop();
        if (!next.getClass().equals(expectedClass)) {
            throw new IllegalStateException("Expected " + expectedClass + ", had " + next);
        }
    }
}
