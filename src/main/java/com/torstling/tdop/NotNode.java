package com.torstling.tdop;

public class NotNode implements BooleanExpressionNode {
    private BooleanExpressionNode expression;

    public NotNode(BooleanExpressionNode expression) {
        this.expression = expression;
    }

    public boolean evaluate(VariableBindings bindings) {
        return !expression.evaluate(bindings);
    }
}
