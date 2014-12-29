package org.bychan.core.dynamic;

import org.bychan.core.basic.LexParser;
import org.bychan.core.basic.Lexer;
import org.bychan.core.basic.Repl;
import org.bychan.core.basic.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A language with a defined syntax.
 *
 * @param <N> The type of nodes which the AST consists of.
 */
public class Language<N> {
    @NotNull
    private final Lexer<N> lexer;
    @NotNull
    private final LexParser<N> lexParser;
    @NotNull
    private final String name;

    public Language(@NotNull final String name, @NotNull final List<TokenDefinition<N>> tokenDefinitions) {
        this.name = name;
        // Use a delegating finder to break the circular dependency between DynamicTokenType
        // and TokenFinder. First build all token types with an empty finder, then build the
        // finder with the resulting DefinitionTokenTypes.
        DelegatingTokenFinder<N> delegatingFinder = new DelegatingTokenFinder<>();
        final Collection<DynamicTokenType<N>> dynamicTokenTypes = toTokenTypes(tokenDefinitions, delegatingFinder);
        Map<TokenKey, DynamicTokenType<N>> tokenTypesByKey = dynamicTokenTypes.stream().collect(Collectors.toMap(DynamicTokenType::getKey, Function.identity()));
        TokenFinder<N> tokenFinder = soughtKey -> {
            DynamicTokenType<N> candidate = tokenTypesByKey.get(soughtKey);
            if (candidate == null) {
                throw new IllegalStateException("No registered token definition keyed '" + soughtKey + "' was found. Did you register your token before referring to it?");
            } else if (candidate.getKey().equals(soughtKey)) {
                return candidate;
            } else {
                throw new IllegalStateException("Found a candidate token definition with the same key ('" + soughtKey + "'), but it had a different specification. Do you have multiple copies of this token definition?");
            }
        };
        delegatingFinder.setDelegate(tokenFinder);
        lexer = new Lexer<>(dynamicTokenTypes);
        lexParser = new LexParser<>(lexer);
    }

    @NotNull
    public Lexer<N> getLexer() {
        return lexer;
    }

    @NotNull
    public LexParser<N> getLexParser() {
        return lexParser;
    }


    private Collection<DynamicTokenType<N>> toTokenTypes(@NotNull final List<TokenDefinition<N>> leveledDefinitions, @NotNull final TokenFinder<N> tokenFinder) {
        return leveledDefinitions.stream().map(tokenDef -> new DynamicTokenType<>(tokenDef, tokenFinder)).collect(Collectors.toList());
    }

    @NotNull
    public Repl repl() {
        return new Repl<>(this);
    }

    @NotNull
    public String getName() {
        return name;
    }


    @NotNull
    public TokenType<N> getTokenType(@NotNull String name) {
        return lexer.getTokenType(name);
    }
}
