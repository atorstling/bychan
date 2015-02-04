package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;

public enum ExpressionType {
    BOOL, FLOAT, INT, LIST;

    @NotNull
    public static ExpressionType union(@NotNull final ExpressionType leftType, @NotNull final ExpressionType rightType) {
        if (!leftType.equals(rightType)) {
            throw new IllegalStateException("Ambiguous type information");
        }
        return leftType;
    }

    @NotNull
    public static ExpressionType forTypeDeclaration(@NotNull final String typeDeclaration) {
        return valueOf(typeDeclaration.toUpperCase());
    }
}
