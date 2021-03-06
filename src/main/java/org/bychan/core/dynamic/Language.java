package org.bychan.core.dynamic;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A language with a defined syntax.
 *
 * @param <N> The type of nodes which the AST consists of.
 */
public class Language<N> {
    @NotNull
    private final String name;
    private final DelegatingTokenFinder<N> tokenFinder;
    private final Collection<DynamicToken<N>> dynamicTokens;

    public Language(@NotNull final String name, @NotNull final List<TokenDefinition<N>> tokenDefinitions, @NotNull final List<TokenDefinitionBuilder<N>> tokenDefinitionBuilders) {
        this.name = name;
        // Use a delegating finder to break the circular dependency between DynamicToken
        // and DynamicTokenFinder. First build all tokens with an empty finder, then build the
        // finder with the result.
        tokenFinder = new DelegatingTokenFinder<>();
        dynamicTokens = tokenDefinitions.stream()
                .map(tokenDef -> new DynamicToken<N>(tokenDef, tokenFinder))
                .collect(Collectors.toList());
        tokenFinder.setDelegate(new TokenFinderImpl<>(dynamicTokens));
        for (TokenDefinition<N> tokenDefinition : tokenDefinitions) {
            tokenDefinition.setTokenFinder(tokenFinder);
        }
        for (TokenDefinitionBuilder<N> builder : tokenDefinitionBuilders) {
            builder.setTokenFinder(tokenFinder);
        }
    }

    @NotNull
    public LexParser<N> newLexParser() {
        return new LexParser<>(newLexer());
    }

    @NotNull
    Lexer<N> newLexer() {
        return new Lexer<>(dynamicTokens);
    }

    @NotNull
    public Repl repl(ParseFunction<N> parseFunction) {
        return new ReplBuilder<>(this, parseFunction).build();
    }

    @NotNull
    public String getName() {
        return name;
    }

}
