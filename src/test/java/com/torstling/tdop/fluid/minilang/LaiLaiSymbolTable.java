package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

interface LaiLaiSymbolTable {
    @NotNull
    Scope getVariables();
}
