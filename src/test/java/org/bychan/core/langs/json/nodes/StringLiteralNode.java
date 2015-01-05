package org.bychan.core.langs.json.nodes;

import org.jetbrains.annotations.NotNull;

public class StringLiteralNode implements JsonNode {
    @NotNull
    private final String text;

    public StringLiteralNode(@NotNull String text) {
        this.text = text;
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

        return text.equals(that.text);

    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @NotNull
    @Override
    public String prettyPrint(int depth) {
        return "\"" + text + "\"";
    }
}
