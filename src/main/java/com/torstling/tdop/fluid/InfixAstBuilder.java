package com.torstling.tdop.fluid;

import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface InfixAstBuilder<N, S> {
    N build(@NotNull S parent, @NotNull LexingMatch match, @NotNull N left, @NotNull ParserCallback2<N, S> parser);
}
