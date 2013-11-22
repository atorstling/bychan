package com.torstling.tdop.fluid;

import com.torstling.tdop.LexingMatch;
import com.torstling.tdop.Node;
import org.jetbrains.annotations.NotNull;

public interface PrefixAstBuilder<N extends Node> {
    N build(@NotNull LexingMatch match, @NotNull ParserCallback2<N> callback);
}
