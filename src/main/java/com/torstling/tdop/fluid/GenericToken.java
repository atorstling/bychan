package com.torstling.tdop.fluid;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class GenericToken<N extends AstNode, S> implements Token<N,S> {
    private final GenericTokenType<N,S> tokenType;
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final LeveledTokenDefinition<N, S> def;
    private final TokenFinder<N, S> tokenFinder;

    public GenericToken(@NotNull final GenericTokenType<N,S> tokenType, @NotNull final LexingMatch match, @NotNull final LeveledTokenDefinition<N, S> def, @NotNull final TokenFinder<N, S> tokenFinder) {
        this.tokenType = tokenType;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public N prefixParse(@NotNull S parent, @NotNull final TokenParserCallback<N,S> parser) {
        final PrefixAstBuilder<N, S> builder = def.getPrefixBuilder();
        if (builder == null) {
            throw new IllegalStateException("Prefix parsing not registered for token type: '" + toString() + "'");
        }
        return builder.build(parent, match, new ParserCallback2<N, S>() {
            @NotNull
            @Override
            public N expression(@NotNull S parent) {
                return parser.tryParse(new ExpressionParserStrategy<N, S>(parent, infixBindingPower())).getRootNode();
            }

            @NotNull
            @Override
            public Token<N,S> expectSingleToken(TokenDefinition<N, S> tokenTypeDefinition) {
                return swallow(tokenTypeDefinition, parser);
            }

            @Override
            public boolean nextIs(@NotNull TokenDefinition<N, S> tokenTypeDefinition) {
                GenericTokenType<N,S> expectedType = tokenFinder.getTokenTypeFor(tokenTypeDefinition);
                return parser.peek().getType().equals(expectedType);
            }
        });
    }

    @NotNull
    private Token<N,S> swallow(TokenDefinition<N, S> tokenD, TokenParserCallback<N,S> parser) {
        GenericTokenType<N,S> type = tokenFinder.getTokenTypeFor(tokenD);
        return parser.swallow(type);
    }

    @NotNull
    @Override
    public N infixParse(S parent, @NotNull final N left, @NotNull final TokenParserCallback<N,S> parser) {
        InfixAstBuilder<N, S> infixBuilder = def.getInfixBuilder();
        if (infixBuilder == null) {
            throw new IllegalStateException("Definition does not support infix parsing: " + this);
        }
        return infixBuilder.build(parent, match, left, new ParserCallback2<N, S>() {
            @NotNull
            @Override
            public N expression(@NotNull S parent) {
                return parser.tryParse(new ExpressionParserStrategy<N, S>(parent, infixBindingPower())).getRootNode();
            }

            @NotNull
            @Override
            public Token<N,S> expectSingleToken(TokenDefinition<N, S> tokenTypeDefinition) {
                return swallow(tokenTypeDefinition, parser);
            }

            @Override
            public boolean nextIs(@NotNull TokenDefinition<N, S> tokenTypeDefinition) {
                GenericTokenType<N,S> expectedType = tokenFinder.getTokenTypeFor(tokenTypeDefinition);
                return parser.peek().getType().equals(expectedType);
            }
        });
    }

    @Override
    public int infixBindingPower() {
        return def.getLevel();
    }

    @Override
    @NotNull
    public TokenType<N,S> getType() {
        return tokenType;
    }

    public String toString() {
        return tokenType.getTokenDefinition().getTokenTypeName() + "(" + match.getText() + ")";
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
