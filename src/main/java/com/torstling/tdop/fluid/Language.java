package com.torstling.tdop.fluid;

import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.utils.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Language<N> {
    private final Lexer<N> lexer;
    private final GenericParser<N> parser;

    public Language(@NotNull final List<TokenDefinitions<N>> tokenDefinitions) {
        List<LeveledTokenDefinition<N>> leveledDefinitions = flatten(tokenDefinitions);
        // Use a delegating finder to break the circular dependency between GenericTokenType
        // and TokenFinder. First build all token types with an empty finder, then build the
        // finder with the resulting DefinitionTokenTypes.
        DelegatingTokenFinder<N> delegatingFinder = new DelegatingTokenFinder<>();
        final Collection<GenericTokenType<N>> genericTokenTypes = toTokenTypes(leveledDefinitions, delegatingFinder);
        TokenFinder<N> tokenFinder = tokenDefinition -> {
            for (GenericTokenType<N> definitionTokenType : genericTokenTypes) {
                if (definitionTokenType.getTokenDefinition().equals(tokenDefinition)) {
                    return definitionTokenType;
                }
            }
            throw new IllegalStateException("No token found for definition " + tokenDefinition);
        };
        delegatingFinder.setDelegate(tokenFinder);
        lexer = new Lexer<>(genericTokenTypes);
        parser = new GenericParser<>(lexer);
    }


    public Lexer<N> getLexer() {
        return lexer;
    }

    public GenericParser<N> getParser() {
        return parser;
    }


    private Collection<GenericTokenType<N>> toTokenTypes(@NotNull final List<LeveledTokenDefinition<N>> leveledDefinitions, @NotNull final TokenFinder<N> tokenFinder) {
        return CollectionUtils.transform(leveledDefinitions, tokenDef -> new GenericTokenType<>(tokenDef, tokenFinder));
    }

    private List<LeveledTokenDefinition<N>> flatten(List<TokenDefinitions<N>> levels) {
        List<LeveledTokenDefinition<N>> flatList = new ArrayList<>();
        int levelCount = 0;
        for (TokenDefinitions<N> level : levels) {
            for (TokenDefinition<N> definition : level) {
                flatList.add(new LeveledTokenDefinition<>(definition, levelCount));
            }
            ++levelCount;
        }
        return flatList;
    }
}
