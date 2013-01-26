package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.ArrayDeque;
import java.util.List;

public class CalculatorParserImpl implements TokenParserCallback {

    @NotNull
    private final ArrayDeque<Token> stack;

    public CalculatorParserImpl(List<? extends Token> tokens) {
        stack = new ArrayDeque<Token>(tokens);
    }

    @NotNull
    public Node parse() {
        return expression(0);
    }

    @NotNull
    public Node expression(int callerInfixBindingPower) {
        Token first = stack.pop();
        Node rootNode = first.suffixParse(this);
        while (callerInfixBindingPower < stack.peek().infixBindingPower()) {
            Token second = stack.pop();
            rootNode = second.infixParse(rootNode, this);
        }
        return rootNode;
    }
}
