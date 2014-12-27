package org.bychan.dynamic;

import org.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StandaloneAstBuilder<N> {
    @NotNull
    N build(@Nullable N previous, @NotNull LexingMatch match);
}
