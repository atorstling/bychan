package com.torstling.tdop.fluid;

import com.torstling.tdop.core.Expression;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alext on 2014-12-06.
 */
class ParserCallback2Impl<N> implements ParserCallback2<N> {
    private final int infixBindingPower;
    @NotNull
    private final TokenFinder<N> tokenFinder;
    @NotNull
    private final TokenParserCallback<N> parser;
    @Nullable
    private final N previous;

    public ParserCallback2Impl(int infixBindingPower, TokenFinder<N> tokenFinder, TokenParserCallback<N> parser, @Nullable final N previous) {
        this.infixBindingPower = infixBindingPower;
        this.tokenFinder = tokenFinder;
        this.parser = parser;
        this.previous = previous;
    }

    @NotNull
    @Override
    public N subExpression(@Nullable N previous) {
        return parser.tryParse(previous, new Expression<>(infixBindingPower)).getRootNode();
    }

    @NotNull
    @Override
    public Token<N> expectSingleToken(TokenDefinition<N> tokenTypeDefinition) {
        return swallow(tokenTypeDefinition, parser);
    }

    @NotNull
    private Token<N> swallow(TokenDefinition<N> tokenD, TokenParserCallback<N> parser) {
        GenericTokenType<N> type = tokenFinder.getTokenTypeFor(tokenD);
        return parser.swallow(type);
    }

    @Override
    public boolean nextIs(@NotNull TokenDefinition<N> tokenTypeDefinition) {
        GenericTokenType<N> expectedType = tokenFinder.getTokenTypeFor(tokenTypeDefinition);
        return parser.peek().getType().equals(expectedType);
    }

    @NotNull
    @Override
    public N parseSingleToken(N previous, TokenDefinition<N> tokenDefinition) {
        Token<N> token = swallow(tokenDefinition, parser);
        return token.prefixParse(previous, parser);
    }

    @NotNull
    @Override
    public N subExpression() {
        return subExpression(previous);
    }
}
