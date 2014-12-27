package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

public class Bychan {
    @NotNull
    public <N> LanguageBuilder<N> newLanguage() {
        return new LanguageBuilder<>();
    }
}
