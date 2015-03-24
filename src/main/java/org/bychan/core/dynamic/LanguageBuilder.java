package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds dynamic languages
 *
 */
public class LanguageBuilder<N> implements TokenDefinitionOwner<N> {
    @NotNull
    private final List<TokenDefinition<N>> tokens;
    @NotNull
    private String name;
    /**
     * If you don't specify binding powers of your tokens,
     * they will be automatically be set in increasing order. This is
     * tracked by this counter.
     */
    private int currentLeftBindingPower;
    /**
     * Counter which numbers unnamed tokens
     */
    private int unnamedTokenCounter;

    public LanguageBuilder() {
        this("unnamed");
    }

    public LanguageBuilder(@NotNull final String name) {
        this.currentLeftBindingPower = 1;
        this.tokens = new ArrayList<>();
        this.name = name;
    }

    @NotNull
    public Language<N> completeLanguage() {
        return new Language<>(name, tokens);
    }

    @NotNull
    public TokenDefinitionBuilder<N> newToken() {
        return new TokenDefinitionBuilder<>(this).leftBindingPower(currentLeftBindingPower);
    }

    @Override
    public int increaseUnnamedTokenCounter() {
        return ++unnamedTokenCounter;
    }

    @Override
    public void tokenBuilt(@NotNull TokenDefinition<N> token) {
        tokens.add(token);
    }

    public void powerUp() {
        ++currentLeftBindingPower;
    }
}
