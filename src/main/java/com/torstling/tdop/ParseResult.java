package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;
import com.sun.istack.internal.Nullable;

public class ParseResult<N extends AstNode> {
    @Nullable
    private final N rootNode;

    private ParseResult(@NotNull final N rootNode) {
        this.rootNode = rootNode;
    }

    @NotNull
    public static <N extends AstNode> ParseResult<N> success(@NotNull final N rootNode) {
        return new ParseResult<>(rootNode);
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
}
