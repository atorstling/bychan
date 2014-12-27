package org.bychan.generic;

import org.bychan.core.LexingMatch;
import org.bychan.core.Token;
import org.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class GenericTokenType<N> implements TokenType<N> {
    @NotNull
    private final TokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;

    public GenericTokenType(@NotNull final TokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public Token<N> toToken(@NotNull final LexingMatch match) {
        return new GenericToken<>(this, match, def, tokenFinder);
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

    public String getTokenTypeName() {
        return def.getTokenTypeName();
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return def;
    }

}
