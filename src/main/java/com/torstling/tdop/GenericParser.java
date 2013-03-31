package com.torstling.tdop;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericParser<N extends Node> {
    @NotNull
    private final Lexer<N> lexer;

    public GenericParser(List<TokenDefinitions<N>> levels) {
        List<LeveledTokenDefinition<N>> leveledDefinitions = flatten(levels);
        DelegatingTokenFinder delegatingFinder = new DelegatingTokenFinder();
        final Collection<DefinitionTokenType<N>> definitionTokenTypes = toTokenTypes(leveledDefinitions, delegatingFinder);
        delegatingFinder.setDelegate(new TokenFinder<N>() {
            @Override
            public DefinitionTokenType<N> getTokenTypeFor(@NotNull TokenDefinition<N> tokenDefinition) {
                for (DefinitionTokenType<N> definitionTokenType : definitionTokenTypes) {
                    if (definitionTokenType.getTokenDefinition().equals(tokenDefinition)) {
                        return definitionTokenType;
                    }
                }
                throw new IllegalStateException("No token found for definition " + tokenDefinition);
            }
        });
        lexer = new Lexer<>(definitionTokenTypes);
    }

    private Collection<DefinitionTokenType<N>> toTokenTypes(@NotNull final List<LeveledTokenDefinition<N>> leveledDefinitions, @NotNull final TokenFinder tokenFinder) {
        return Collections2.transform(leveledDefinitions, new Function<LeveledTokenDefinition<N>, DefinitionTokenType<N>>() {
            @Override
            public DefinitionTokenType<N> apply(@NotNull final LeveledTokenDefinition<N> tokenDef) {
                return new DefinitionTokenType<N>(tokenDef, tokenFinder);
            }
        });
    }

    private List<LeveledTokenDefinition<N>> flatten(List<TokenDefinitions<N>> levels) {
        List<LeveledTokenDefinition<N>> flatList = new ArrayList<LeveledTokenDefinition<N>>();
        int levelCount=0;
        for (TokenDefinitions<N> level : levels) {
            for (TokenDefinition<N> definition : level) {
                flatList.add(new LeveledTokenDefinition<N>(definition, levelCount));
            }
            ++levelCount;
        }
        return flatList;
    }

    @NotNull
    public ParseResult<N> parse(@NotNull final String text) {
        List<Token<N>> tokens = lexer.lex(text);
        PrattParser<N> parser = new PrattParser<>(tokens);
        N rootNode = parser.parse();
        return ParseResult.success(rootNode);
    }
}
