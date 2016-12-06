package org.bychan.core.langs.calculator.nodes;

import org.bychan.core.basic.Evaluatable;

public interface CalculatorNode extends Evaluatable<Integer> {
    Integer evaluate();
}
