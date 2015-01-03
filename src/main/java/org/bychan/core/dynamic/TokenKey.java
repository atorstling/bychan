package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

/**
 * Used to refer to other tokens
 */
class TokenKey {
    @NotNull
    private final String tokenName;

    TokenKey(@NotNull String tokenName) {
        this.tokenName = tokenName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenKey tokenKey = (TokenKey) o;

        return tokenName.equals(tokenKey.tokenName);

    }

    @Override
    public int hashCode() {
        return tokenName.hashCode();
    }

    @Override
    public String toString() {
        return "token(" + tokenName + ")";
    }
}

