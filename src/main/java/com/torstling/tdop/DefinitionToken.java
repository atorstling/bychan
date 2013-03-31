package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class DefinitionToken<N extends Node> implements Token<N> {
    private DefinitionTokenType<N> tokenType;
    @NotNull
    private LexingMatch match;
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private TokenFinder<N> tokenFinder;

    public DefinitionToken(@NotNull final DefinitionTokenType<N> tokenType, @NotNull final LexingMatch match, @NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.tokenType = tokenType;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public N prefixParse(@NotNull final TokenParserCallback<N> parser) {
        final PrefixAstBuilder<N> builder = def.getPrefixBuilder();
        return builder.build(match, new ParserCallback2<N>() {
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
        DefinitionTokenType<N> type = tokenFinder.getTokenTypeFor(tokenD);
        parser.swallow(type);
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

    @Override
    public boolean isOfType(@NotNull TokenType<N> type) {
        return type.equals(tokenType);
    }

    public String toString() {
        return "" + def;
    }
}
