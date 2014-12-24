package org.bychan.core;

import org.bychan.fluid.FluidLanguageBuilder;
import org.jetbrains.annotations.NotNull;

public class Bychan {
    @NotNull
    public <N> FluidLanguageBuilder<N> newFluidLanguage() {
        return new FluidLanguageBuilder<>();
    }
}
