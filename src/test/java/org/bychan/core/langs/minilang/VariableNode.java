package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;

interface VariableNode extends LaiLaiNode {
    public void assign(@NotNull Object value, @NotNull ScopeNode currentScope);
}
