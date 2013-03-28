package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class ParseResult<N extends Node> {
    @NotNull
    public static <N extends Node> ParseResult<N> success() {
        return new ParseResult<N>();
    }
}
