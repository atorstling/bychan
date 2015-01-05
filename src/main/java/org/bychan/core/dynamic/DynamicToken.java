package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class DynamicToken<N> implements Token<N> {
    @NotNull
    private final TokenDefinition<N> def;
    @NotNull
    private final DynamicTokenFinder<N> tokenFinder;

    public DynamicToken(@NotNull final TokenDefinition<N> def, @NotNull final DynamicTokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public Lexeme<N> toLexeme(@NotNull final LexingMatch match) {
        return new DynamicLexeme<>(this, match, def, tokenFinder);
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
        return def.tokenName();
    }

    public String toString() {
        return def.getLeftBindingPower() + ":" + getName();
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return def;
    }

    @NotNull
    public String getTokenName() {
        return def.tokenName();
    }
}
