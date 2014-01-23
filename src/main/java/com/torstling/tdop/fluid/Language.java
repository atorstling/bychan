package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.utils.CollectionUtils;
import com.torstling.tdop.utils.Function;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Language<N extends AstNode,S> {
    private final Lexer<N,S> lexer;
    private final GenericParser<N,S> parser;

    public Language(@NotNull final List<TokenDefinitions<N, S>> tokenDefinitions) {
        List<LeveledTokenDefinition<N, S>> leveledDefinitions = flatten(tokenDefinitions);
        // Use a delegating finder to break the circular dependency between GenericTokenType
        // and TokenFinder. First build all token types with an empty finder, then build the
        // finder with the resulting DefinitionTokenTypes.
        DelegatingTokenFinder<N, S> delegatingFinder = new DelegatingTokenFinder<N, S>();
        final Collection<GenericTokenType<N,S>> genericTokenTypes = toTokenTypes(leveledDefinitions, delegatingFinder);
        TokenFinder<N, S> tokenFinder = new TokenFinder<N, S>() {
            @NotNull
            @Override
            public GenericTokenType<N,S> getTokenTypeFor(@NotNull TokenDefinition<N, S> tokenDefinition) {
                for (GenericTokenType<N,S> definitionTokenType : genericTokenTypes) {
                    if (definitionTokenType.getTokenDefinition().equals(tokenDefinition)) {
                        return definitionTokenType;
                    }
                }
                throw new IllegalStateException("No token found for definition " + tokenDefinition);
            }
        };
        delegatingFinder.setDelegate(tokenFinder);
        lexer = new Lexer<>(genericTokenTypes);
        parser = new GenericParser<N,S>(lexer);
    }


    public Lexer<N,S> getLexer() {
        return lexer;
    }

    public GenericParser<N,S> getParser() {
        return parser;
    }


    private Collection<GenericTokenType<N,S>> toTokenTypes(@NotNull final List<LeveledTokenDefinition<N, S>> leveledDefinitions, @NotNull final TokenFinder<N, S> tokenFinder) {
        return CollectionUtils.transform(leveledDefinitions, new Function<LeveledTokenDefinition<N, S>, GenericTokenType<N,S>>() {
            @NotNull
            @Override
            public GenericTokenType<N,S> apply(@NotNull final LeveledTokenDefinition<N, S> tokenDef) {
                return new GenericTokenType<>(tokenDef, tokenFinder);
            }
        });
    }

    private List<LeveledTokenDefinition<N, S>> flatten(List<TokenDefinitions<N, S>> levels) {
        List<LeveledTokenDefinition<N, S>> flatList = new ArrayList<>();
        int levelCount = 0;
        for (TokenDefinitions<N, S> level : levels) {
            for (TokenDefinition<N, S> definition : level) {
                flatList.add(new LeveledTokenDefinition<N, S>(definition, levelCount));
            }
            ++levelCount;
        }
        return flatList;
    }
}
