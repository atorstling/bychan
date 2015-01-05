package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface LaiLaiNode {
    @NotNull
    Object evaluate(@Nullable ScopeNode currentScope);

    @NotNull
    ExpressionType getExpressionType(@Nullable ScopeNode currentScope);

    @Nullable
    Scope getScope();
}
