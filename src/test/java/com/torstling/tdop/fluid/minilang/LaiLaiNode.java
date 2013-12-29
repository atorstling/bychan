package com.torstling.tdop.fluid.minilang;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface LaiLaiNode extends AstNode {
    @NotNull
    Object evaluate();

    @NotNull
    ExpressionType getExpressionType();

    @NotNull
    Map<String, VariableNode> getVariables();
}
