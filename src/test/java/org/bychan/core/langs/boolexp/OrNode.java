package org.bychan.core.langs.boolexp;

import org.jetbrains.annotations.NotNull;

public class OrNode implements BooleanExpressionNode {
    private final BooleanExpressionNode previous;
    private final BooleanExpressionNode right;

    public OrNode(BooleanExpressionNode previous, BooleanExpressionNode right) {
        this.previous = previous;
        this.right = right;
    }

    public boolean evaluate(@NotNull VariableBindings bindings) {
        boolean previousResult = previous.evaluate(bindings);
        return previousResult || right.evaluate(bindings);
    }
}
