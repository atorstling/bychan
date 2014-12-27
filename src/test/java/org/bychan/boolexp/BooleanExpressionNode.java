package org.bychan.boolexp;

import org.bychan.core.AstNode;
import org.jetbrains.annotations.NotNull;

public interface BooleanExpressionNode extends AstNode {
    boolean evaluate(@NotNull VariableBindings bindings);
}
