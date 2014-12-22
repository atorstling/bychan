package com.torstling.bychan.fluid.minilang;

import com.torstling.bychan.core.AstNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface LaiLaiNode extends AstNode {
    @NotNull
    Object evaluate(@Nullable ScopeNode currentScope);

    @NotNull
    ExpressionType getExpressionType(@Nullable ScopeNode currentScope);

    @Nullable
    Scope getScope();
}
