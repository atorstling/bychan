package com.torstling.tdop.fluid;

import com.torstling.tdop.AstNode;
import com.torstling.tdop.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface PrefixAstBuilder<N extends AstNode> {
    N build(@NotNull LexingMatch match, @NotNull ParserCallback2<N> callback);
}
