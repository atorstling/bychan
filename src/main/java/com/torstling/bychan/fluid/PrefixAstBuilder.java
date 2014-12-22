package com.torstling.bychan.fluid;

import com.torstling.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PrefixAstBuilder<N> {
    @NotNull
    N build(@Nullable N previous, @NotNull LexingMatch match, @NotNull FluidParserCallback<N> parser);
}
