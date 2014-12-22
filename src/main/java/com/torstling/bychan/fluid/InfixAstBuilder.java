package com.torstling.bychan.fluid;

import com.torstling.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InfixAstBuilder<N> {
    N build(@NotNull LexingMatch match, @Nullable N previous, @NotNull ParserCallback2<N> parser);
}
