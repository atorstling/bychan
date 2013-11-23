package com.torstling.tdop.fluid;

import com.google.common.base.Function;
import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericParser<N extends AstNode> {
    @NotNull
    private final Lexer<N> lexer;

    public GenericParser(List<TokenDefinitions<N>> levels) {
        List<LeveledTokenDefinition<N>> leveledDefinitions = flatten(levels);
        // Use a delegating finder to break the circular dependency between GenericTokenType
        // and TokenFinder. First build all token types with an empty finder, then build the
        // finder with the resulting DefinitionTokenTypes.
        DelegatingTokenFinder<N> delegatingFinder = new DelegatingTokenFinder<>();
        final Collection<GenericTokenType<N>> genericTokenTypes = toTokenTypes(leveledDefinitions, delegatingFinder);
        TokenFinder<N> tokenFinder = new TokenFinder<N>() {
            @NotNull
            @Override
            public GenericTokenType<N> getTokenTypeFor(@NotNull TokenDefinition<N> tokenDefinition) {
                for (GenericTokenType<N> definitionTokenType : genericTokenTypes) {
                    if (definitionTokenType.getTokenDefinition().equals(tokenDefinition)) {
                        return definitionTokenType;
                    }
                }
                throw new IllegalStateException("No token found for definition " + tokenDefinition);
            }
        };
        delegatingFinder.setDelegate(tokenFinder);
        lexer = new Lexer<>(genericTokenTypes);
    }

    private Collection<GenericTokenType<N>> toTokenTypes(@NotNull final List<LeveledTokenDefinition<N>> leveledDefinitions, @NotNull final TokenFinder<N> tokenFinder) {
        return Utils2.transform(leveledDefinitions, new Function<LeveledTokenDefinition<N>, GenericTokenType<N>>() {
            @Override
            public GenericTokenType<N> apply(final LeveledTokenDefinition<N> tokenDef) {
                return new GenericTokenType<>(tokenDef, tokenFinder);
            }
        });
    }

    private List<LeveledTokenDefinition<N>> flatten(List<TokenDefinitions<N>> levels) {
        List<LeveledTokenDefinition<N>> flatList = new ArrayList<>();
        int levelCount=0;
        for (TokenDefinitions<N> level : levels) {
            for (TokenDefinition<N> definition : level) {
                flatList.add(new LeveledTokenDefinition<>(definition, levelCount));
            }
            ++levelCount;
        }
        return flatList;
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull final String text) {
        List<Token<N>> tokens = lexer.lex(text);
        PrattParser<N> parser = new PrattParser<>(tokens);
        return parser.tryParse();
    }
}
