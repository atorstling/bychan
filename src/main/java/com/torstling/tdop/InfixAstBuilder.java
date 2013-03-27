package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface InfixAstBuilder<N extends Node> {
    BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull BooleanExpressionNode left, @NotNull ParserCallback2<N> parser);
}
