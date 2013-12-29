package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface PrefixAstBuilder<N extends AstNode> {
    @NotNull
    N build(@NotNull N parent, @NotNull LexingMatch match, @NotNull ParserCallback2<N> parser);
}
