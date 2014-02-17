package com.torstling.tdop.fluid.json;

import org.jetbrains.annotations.NotNull;

public class StringLiteralNode implements JsonNode {
    @NotNull
    private final String text;

    public StringLiteralNode(@NotNull String text) {
        this.text = text;
    }


    @Override
    public Object evaluate() {
        return text;
    }
}
