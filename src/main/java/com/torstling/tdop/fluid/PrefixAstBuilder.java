package com.torstling.tdop.fluid;

import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PrefixAstBuilder<N> {
    @NotNull
    N build(@Nullable N previous, @NotNull LexingMatch match, @NotNull ParserCallback2<N> parser);
}
