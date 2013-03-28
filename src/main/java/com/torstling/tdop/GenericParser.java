package com.torstling.tdop;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericParser<N extends Node> {
    public GenericParser(List<TokenDefinitions<N>> levels) {
        List<LeveledTokenDefinition<N>> leveledDefinitions = flatten(levels);
        Collection<WrappedDefinition<N>> wrappedDefinitions = Collections2.transform(leveledDefinitions, new Function<LeveledTokenDefinition<N>, WrappedDefinition<N>>() {
            @Override
            public WrappedDefinition<N> apply(LeveledTokenDefinition<N> tokenDef) {
                return new WrappedDefinition<N>(tokenDef);
            }
        });
        new Lexer<N>(wrappedDefinitions);
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
        return ParseResult.<N>success();
    }
}
