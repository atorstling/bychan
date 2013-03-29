package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class DefinitionToken<N extends Node> implements Token<N> {
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private TokenFinder<N> tokenFinder;

    public DefinitionToken(@NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public N prefixParse(@NotNull final TokenParserCallback<N> parser) {
        final PrefixAstBuilder<N> builder = def.getPrefixBuilder();
        return builder.build(new LexingMatch("fake"), new ParserCallback2<N>() {
            @Override
            public N expression() {
                return parser.expression(infixBindingPower());
            }

            @Override
            public LexingMatch expect(TokenDefinition<N> tokenD) {
                return swallow(tokenD, parser);
            }
        });
    }

    private LexingMatch swallow(TokenDefinition<N> tokenD, TokenParserCallback<N> parser) {
        Class<? extends Token<N>> aClass = (Class) tokenFinder.getTokenFor(tokenD).getClass();
        parser.swallow(aClass);
        return new LexingMatch("fake");
    }

    @NotNull
    @Override
    public N infixParse(@NotNull final N left, @NotNull final TokenParserCallback<N> parser) {
        InfixAstBuilder<N> infixBuilder = def.getInfixBuilder();
        return infixBuilder.build(new LexingMatch("fake"), left, new ParserCallback2<N>() {
            @Override
            public N expression() {
                return parser.expression(infixBindingPower());
            }

            @Override
            public LexingMatch expect(TokenDefinition<N> tokenD) {
                return swallow(tokenD, parser);
            }
        });
    }

    @Override
    public int infixBindingPower() {
        return def.getLevel();
    }

    public String toString() {
        return "" + def;
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return def.getTokenDefinition();
    }
}
