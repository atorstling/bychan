package org.bychan.generic;

import org.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InfixAstBuilder<N> {
    N build(@NotNull LexingMatch match, @Nullable N previous, @NotNull UserParserCallback<N> parser);
}
