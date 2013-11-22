package com.torstling.tdop.fluid;

import com.google.common.base.Function;
import com.torstling.tdop.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericParser<N extends AstNode> {
    @NotNull
    private final Lexer<N> lexer;

    public GenericParser(List<TokenDefinitions<N>> levels) {
        List<LeveledTokenDefinition<N>> leveledDefinitions = flatten(levels);
        DelegatingTokenFinder<N> delegatingFinder = new DelegatingTokenFinder<>();
        final Collection<DefinitionTokenType<N>> definitionTokenTypes = toTokenTypes(leveledDefinitions, delegatingFinder);
        delegatingFinder.setDelegate(new TokenFinder<N>() {
            @NotNull
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

    private Collection<DefinitionTokenType<N>> toTokenTypes(@NotNull final List<LeveledTokenDefinition<N>> leveledDefinitions, @NotNull final TokenFinder<N> tokenFinder) {
        return Utils2.transform(leveledDefinitions, new Function<LeveledTokenDefinition<N>, DefinitionTokenType<N>>() {
            @Override
            public DefinitionTokenType<N> apply(final LeveledTokenDefinition<N> tokenDef) {
                return new DefinitionTokenType<>(tokenDef, tokenFinder);
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
    public ParseResult<N> parse(@NotNull final String text) {
        List<Token<N>> tokens = lexer.lex(text);
        PrattParser<N> parser = new PrattParser<>(tokens);
        N rootNode = parser.parse();
        return ParseResult.success(rootNode);
    }
}
