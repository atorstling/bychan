package com.torstling.tdop.fluid.minilang;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LaiLaiNode extends AstNode {
    @NotNull
    Object evaluate(@Nullable ScopeNode currentScope);

    @NotNull
    ExpressionType getExpressionType(@Nullable ScopeNode currentScope);
}
