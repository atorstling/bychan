package org.bychan.calculator.nodes;

import org.bychan.core.AstNode;

public interface CalculatorNode extends AstNode {
    int evaluate();
}
