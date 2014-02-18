package com.torstling.tdop.fluid;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class GenericToken<N, S> implements Token<N, S> {
    private final GenericTokenType<N, S> tokenType;
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final LeveledTokenDefinition<N, S> def;
    private final TokenFinder<N, S> tokenFinder;

    public GenericToken(@NotNull final GenericTokenType<N, S> tokenType, @NotNull final LexingMatch match, @NotNull final LeveledTokenDefinition<N, S> def, @NotNull final TokenFinder<N, S> tokenFinder) {
        this.tokenType = tokenType;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public N prefixParse(@NotNull N previous, @NotNull final TokenParserCallback<N, S> parser) {
        final PrefixAstBuilder<N, S> builder = def.getPrefixBuilder();
        if (builder == null) {
            throw new IllegalStateException("Prefix parsing not registered for token type: '" + toString() + "'");
        }
        return builder.build(previous, match, new ParserCallback2<N, S>() {
            @NotNull
            @Override
            public N expression(@NotNull N previous) {
                return parser.tryParse(new ExpressionParserStrategy<>(previous, infixBindingPower())).getRootNode();
            }

            @NotNull
            @Override
            public Token<N, S> expectSingleToken(TokenDefinition<N, S> tokenTypeDefinition) {
                return swallow(tokenTypeDefinition, parser);
            }

            @Override
            public boolean nextIsNot(@NotNull TokenDefinition<N, S> tokenTypeDefinition) {
                GenericTokenType<N, S> expectedType = tokenFinder.getTokenTypeFor(tokenTypeDefinition);
                return !parser.peek().getType().equals(expectedType);
            }
        });
    }

    @NotNull
    private Token<N, S> swallow(TokenDefinition<N, S> tokenD, TokenParserCallback<N, S> parser) {
        GenericTokenType<N, S> type = tokenFinder.getTokenTypeFor(tokenD);
        return parser.swallow(type);
    }

    @NotNull
    @Override
    public N infixParse(@NotNull final N previous, @NotNull final TokenParserCallback<N, S> parser) {
        InfixAstBuilder<N, S> infixBuilder = def.getInfixBuilder();
        if (infixBuilder == null) {
            throw new IllegalStateException("Definition does not support infix parsing: " + this);
        }
        return infixBuilder.build(match, previous, new ParserCallback2<N, S>() {
            @NotNull
            @Override
            public N expression(@NotNull N previous) {
                return parser.tryParse(new ExpressionParserStrategy<>(previous, infixBindingPower())).getRootNode();
            }

            @NotNull
            @Override
            public Token<N, S> expectSingleToken(TokenDefinition<N, S> tokenTypeDefinition) {
                return swallow(tokenTypeDefinition, parser);
            }

            @Override
            public boolean nextIsNot(@NotNull TokenDefinition<N, S> tokenTypeDefinition) {
                GenericTokenType<N, S> expectedType = tokenFinder.getTokenTypeFor(tokenTypeDefinition);
                return !parser.peek().getType().equals(expectedType);
            }
        });
    }

    @Override
    public int infixBindingPower() {
        return def.getLevel();
    }

    @Override
    @NotNull
    public TokenType<N, S> getType() {
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
