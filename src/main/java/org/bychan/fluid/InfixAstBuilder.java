package org.bychan.fluid;

import org.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InfixAstBuilder<N> {
    N build(@NotNull LexingMatch match, @Nullable N previous, @NotNull FluidParserCallback<N> parser);
}
