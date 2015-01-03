package org.bychan.core.dynamic;

import org.bychan.core.basic.LexParser;
import org.bychan.core.basic.Lexer;
import org.bychan.core.basic.Repl;
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
    @NotNull
    private final Map<TokenKey, DynamicToken<N>> tokensByKey;

    public Language(@NotNull final String name, @NotNull final List<TokenDefinition<N>> tokenDefinitions) {
        this.name = name;
        // Use a delegating finder to break the circular dependency between DynamicToken
        // and DynamicTokenFinder. First build all tokens with an empty finder, then build the
        // finder with the result.
        DelegatingTokenFinder<N> delegatingFinder = new DelegatingTokenFinder<>();
        final Collection<DynamicToken<N>> dynamicTokens = toTokens(tokenDefinitions, delegatingFinder);
        tokensByKey = dynamicTokens.stream().collect(Collectors.toMap(DynamicToken::getKey, Function.identity()));
        delegatingFinder.setDelegate(this::findTokenByKey);
        lexer = new Lexer<>(dynamicTokens);
        lexParser = new LexParser<>(lexer);
    }

    @NotNull
    private DynamicToken<N> findTokenByKey(@NotNull TokenKey soughtKey) {
        DynamicToken<N> candidate = tokensByKey.get(soughtKey);
        if (candidate == null) {
            throw new IllegalStateException("No registered token definition keyed '" + soughtKey + "' was found. Did you register your token before referring to it?");
        } else if (candidate.getKey().equals(soughtKey)) {
            return candidate;
        } else {
            throw new IllegalStateException("Found a candidate token definition with the same key ('" + soughtKey + "'), but it had a different specification. Do you have multiple copies of this token definition?");
        }
    }

    @NotNull
    public Lexer<N> getLexer() {
        return lexer;
    }

    @NotNull
    public LexParser<N> getLexParser() {
        return lexParser;
    }


    private Collection<DynamicToken<N>> toTokens(@NotNull final List<TokenDefinition<N>> leveledDefinitions, @NotNull final DynamicTokenFinder<N> tokenFinder) {
        return leveledDefinitions.stream().map(tokenDef -> new DynamicToken<>(tokenDef, tokenFinder)).collect(Collectors.toList());
    }

    @NotNull
    public Repl repl() {
        return new Repl<>(this);
    }

    @NotNull
    public String getName() {
        return name;
    }
}
