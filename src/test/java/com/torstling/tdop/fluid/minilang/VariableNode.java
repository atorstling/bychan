package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public interface VariableNode extends LaiLaiNode {
    public void assign(@NotNull Object value, @NotNull ScopeNode currentScope);
}
