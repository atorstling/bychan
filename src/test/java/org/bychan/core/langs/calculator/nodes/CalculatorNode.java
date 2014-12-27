package org.bychan.core.langs.calculator.nodes;

import org.bychan.core.basic.AstNode;

public interface CalculatorNode extends AstNode {
    int evaluate();
}
