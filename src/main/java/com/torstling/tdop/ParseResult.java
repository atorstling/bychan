package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;
import com.sun.istack.internal.Nullable;

public class ParseResult<N extends Node> {
    @Nullable
    private final N rootNode;

    public ParseResult(@NotNull final N rootNode) {
        this.rootNode = rootNode;
    }

    @NotNull
    public static <N extends Node> ParseResult<N> success(@NotNull final N rootNode) {
        return new ParseResult<N>(rootNode);
    }

    public boolean isSuccess() {
        return rootNode != null;
    }

    private void checkSuccess() {
        if (!isSuccess()) {
            throw new IllegalStateException("Parsing wasn't successful");
        }
    }

    @NotNull
    public N getRootNode() {
        checkSuccess();
        return rootNode;
    }

    public boolean isFailure() {
        return !isSuccess();
    }
}
