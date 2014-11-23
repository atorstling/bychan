package com.torstling.tdop.fluid.json.nodes;

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

    @Override
    public String toString() {
        return "StringLiteralNode{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringLiteralNode that = (StringLiteralNode) o;

        if (!text.equals(that.text)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
