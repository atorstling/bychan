package com.torstling.tdop.calculator.nodes;

import com.torstling.tdop.core.AstNode;

public interface CalculatorNode extends AstNode {
    int evaluate();
}
