package org.bychan.core.dynamic;

import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class DynamicTokenType<N> implements TokenType<N> {
    @NotNull
    private final TokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;

    public DynamicTokenType(@NotNull final TokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public Token<N> toToken(@NotNull final LexingMatch match) {
        return new DynamicToken<>(this, match, def, tokenFinder);
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        return def.getPattern();
    }

    @Override
    public boolean include() {
        return def.parse();
    }

    @NotNull
    @Override
    public String getName() {
        return def.getTokenTypeName();
    }

    public String toString() {
        return def.getLeftBindingPower() + ":" + getName();
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return def;
    }

    @NotNull
    public TokenKey getKey() {
        return def.getKey();
    }
}
