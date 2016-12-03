package org.bychan.core.dynamic;

import org.bychan.core.basic.LexParser;
import org.bychan.core.basic.Lexer;
import org.bychan.core.basic.Repl;
import org.bychan.core.basic.ReplBuilder;
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
    @NotNull
    private final List<TokenDefinition<N>> tokenDefinitions;

    public Language(@NotNull final String name, @NotNull final List<TokenDefinition<N>> tokenDefinitions) {
        this.name = name;
        this.tokenDefinitions = tokenDefinitions;
    }

    @NotNull
    public LexParser<N> newLexParser() {
        return new LexParser<>(newLexer());
    }

    @NotNull
    Lexer<N> newLexer() {
        // Use a delegating finder to break the circular dependency between DynamicToken
        // and DynamicTokenFinder. First build all tokens with an empty finder, then build the
        // finder with the result.
        DelegatingTokenFinder<N> delegatingFinder = new DelegatingTokenFinder<>();
        final Collection<DynamicToken<N>> dynamicTokens = toTokens(tokenDefinitions, delegatingFinder);
        delegatingFinder.setDelegate(new TokenFinderImpl<>(dynamicTokens));
        return new Lexer<>(dynamicTokens);
    }


    private Collection<DynamicToken<N>> toTokens(@NotNull final List<TokenDefinition<N>> leveledDefinitions, @NotNull final TokenFinder<N> tokenFinder) {
        return leveledDefinitions.stream().map(tokenDef -> new DynamicToken<>(tokenDef, tokenFinder)).collect(Collectors.toList());
    }

    @NotNull
    public Repl repl() {
        return new ReplBuilder<>(this).build();
    }

    @NotNull
    public String getName() {
        return name;
    }

}
