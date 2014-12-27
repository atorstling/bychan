package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.AstNode;
import org.jetbrains.annotations.NotNull;

public interface BooleanExpressionNode extends AstNode {
    boolean evaluate(@NotNull VariableBindings bindings);
}
