package com.torstling.tdop;

public interface BooleanExpressionNode extends Node {
    boolean evaluate(VariableBindings bindings);
}
