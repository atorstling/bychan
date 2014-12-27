package org.bychan.generic;

import org.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InfixAstBuilder<N> {
    N build(@Nullable N previous, @NotNull LexingMatch match, @NotNull UserParserCallback<N> parser);
}
