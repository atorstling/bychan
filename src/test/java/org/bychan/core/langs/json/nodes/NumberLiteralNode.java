package org.bychan.core.langs.json.nodes;

import org.jetbrains.annotations.NotNull;

public class NumberLiteralNode implements JsonNode {
    @NotNull
    private final String value;

    public static NumberLiteralNode fromFloat(float f) {
        return new NumberLiteralNode(toString(f));
    }

    /**
     * http://stackoverflow.com/a/14126736/83741
     */
    @NotNull
    private static String toString(float f) {
        if (f == (long) f) {
            return String.format("%d", (long) f);
        }
        return String.format("%s", f);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberLiteralNode that = (NumberLiteralNode) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public NumberLiteralNode(@NotNull String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @NotNull
    @Override
    public String prettyPrint(int depth) {
        return value;
    }
}
