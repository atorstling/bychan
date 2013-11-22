package com.torstling.tdop.calculator;

import com.torstling.tdop.core.AstNode;

public interface CalculatorNode extends AstNode {
    int evaluate();
}
