package com.torstling.bychan.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public enum ExpressionType {
    BOOL, FLOAT, INT, LIST;

    @NotNull
    public static ExpressionType union(@NotNull final ExpressionType previousType, @NotNull final ExpressionType rightType) {
        if (!previousType.equals(rightType)) {
            throw new IllegalStateException("Ambiguous type information");
        }
        return previousType;
    }

    @NotNull
    public static ExpressionType forTypeDeclaration(@NotNull final String typeDeclaration) {
        return valueOf(typeDeclaration.toUpperCase());
    }
}
