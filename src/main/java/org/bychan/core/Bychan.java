package org.bychan.core;

import org.bychan.generic.LanguageBuilder;
import org.jetbrains.annotations.NotNull;

public class Bychan {
    @NotNull
    public <N> LanguageBuilder<N> newFluidLanguage() {
        return new LanguageBuilder<>();
    }
}
