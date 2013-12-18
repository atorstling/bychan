package com.torstling.tdop.fluid;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class GenericToken<N extends AstNode> implements Token<N> {
    private final GenericTokenType<N> tokenType;
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;

    public GenericToken(@NotNull final GenericTokenType<N> tokenType, @NotNull final LexingMatch match, @NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
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
            @NotNull
            @Override
            public N expression() {
                return parser.expression(infixBindingPower());
            }

            @NotNull
            @Override
            public Token<N> expectSingleToken(TokenDefinition<N> tokenTypeDefinition) {
                return swallow(tokenTypeDefinition, parser);
            }
        });
    }

    @NotNull
    private Token<N> swallow(TokenDefinition<N> tokenD, TokenParserCallback<N> parser) {
        GenericTokenType<N> type = tokenFinder.getTokenTypeFor(tokenD);
        return parser.swallow(type);
    }

    @NotNull
    @Override
    public N infixParse(@NotNull final N left, @NotNull final TokenParserCallback<N> parser) {
        InfixAstBuilder<N> infixBuilder = def.getInfixBuilder();
        return infixBuilder.build(match, left, new ParserCallback2<N>() {
            @NotNull
            @Override
            public N expression() {
                return parser.expression(infixBindingPower());
            }

            @NotNull
            @Override
            public Token<N> expectSingleToken(TokenDefinition<N> tokenTypeDefinition) {
                return swallow(tokenTypeDefinition, parser);
            }
        });
    }

    @Override
    public int infixBindingPower() {
        return def.getLevel();
    }

    @Override
    @NotNull
    public TokenType<N> getType() {
        return tokenType;
    }

    public String toString() {
        return "" + def;
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
