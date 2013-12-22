package com.torstling.tdop.fluid.minilang;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

public interface LaiLaiNode extends AstNode {
    @NotNull
    Object evaluate();
}
