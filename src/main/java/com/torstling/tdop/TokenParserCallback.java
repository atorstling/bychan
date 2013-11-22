package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public interface TokenParserCallback<N extends AstNode> {
    @NotNull
    N expression(int rightBindingPower);
    @NotNull
    Token<N> swallow(@NotNull TokenType<N> type);
}
