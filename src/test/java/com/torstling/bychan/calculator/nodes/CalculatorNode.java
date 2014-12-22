package com.torstling.bychan.calculator.nodes;

import com.torstling.bychan.core.AstNode;

public interface CalculatorNode extends AstNode {
    int evaluate();
}
