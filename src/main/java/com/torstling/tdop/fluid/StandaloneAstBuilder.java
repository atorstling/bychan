package com.torstling.tdop.fluid;

import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface StandaloneAstBuilder<N, S> {
    @NotNull
    N build(@NotNull N previous, @NotNull LexingMatch match);
}
