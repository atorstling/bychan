package com.torstling.tdop.fluid;

import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface PrefixAstBuilder<N, S> {
    @NotNull
    N build(@NotNull N previous, @NotNull LexingMatch match, @NotNull ParserCallback2<N, S> parser);
}
