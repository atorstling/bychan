package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

/**
 * Used to refer to other tokens
 */
class TokenKey {
    @NotNull
    private final String tokenTypeName;

    TokenKey(@NotNull String tokenTypeName) {
        this.tokenTypeName = tokenTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenKey tokenKey = (TokenKey) o;

        return tokenTypeName.equals(tokenKey.tokenTypeName);

    }

    @Override
    public int hashCode() {
        return tokenTypeName.hashCode();
    }

    @Override
    public String toString() {
        return "token(" + tokenTypeName + ")";
    }
}

