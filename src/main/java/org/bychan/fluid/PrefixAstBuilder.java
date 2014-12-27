package org.bychan.fluid;

import org.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PrefixAstBuilder<N> {
    @NotNull
    N build(@Nullable N previous, @NotNull LexingMatch match, @NotNull UserParserCallback<N> parser);
}
