package org.bychan.generic;

import org.bychan.core.LexParser;
import org.bychan.core.Lexer;
import org.bychan.core.Repl;
import org.bychan.core.TokenType;
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
        // Use a delegating finder to break the circular dependency between GenericTokenType
        // and TokenFinder. First build all token types with an empty finder, then build the
        // finder with the resulting DefinitionTokenTypes.
        DelegatingTokenFinder<N> delegatingFinder = new DelegatingTokenFinder<>();
        final Collection<GenericTokenType<N>> genericTokenTypes = toTokenTypes(tokenDefinitions, delegatingFinder);
        Map<String, GenericTokenType<N>> tokenTypesByTokenTypeName = genericTokenTypes.stream().collect(Collectors.toMap(GenericTokenType::getTokenTypeName, Function.identity()));
        TokenFinder<N> tokenFinder = sought -> {
            String soughtName = sought.getTokenTypeName();
            GenericTokenType<N> candidate = tokenTypesByTokenTypeName.get(soughtName);
            if (candidate == null) {
                throw new IllegalStateException("No registered token definition named ''" + soughtName + "' was found. Did you register your token before referring to it?");
            } else if (candidate.getTokenDefinition().equals(sought)) {
                return candidate;
            } else {
                throw new IllegalStateException("Found a candidate token definition with the same name ('" + soughtName + "'), but it had a different specification. Do you have multiple copies of this token definition?");
            }
        };
        delegatingFinder.setDelegate(tokenFinder);
        lexer = new Lexer<>(genericTokenTypes);
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


    private Collection<GenericTokenType<N>> toTokenTypes(@NotNull final List<TokenDefinition<N>> leveledDefinitions, @NotNull final TokenFinder<N> tokenFinder) {
        return leveledDefinitions.stream().map(tokenDef -> new GenericTokenType<>(tokenDef, tokenFinder)).collect(Collectors.toList());
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
