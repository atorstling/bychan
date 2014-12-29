package org.bychan.core.dynamic;

import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenParserCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class UserParserCallbackImpl<N> implements UserParserCallback<N> {
    private final int leftBindingPower;
    @NotNull
    private final TokenFinder<N> tokenFinder;
    @NotNull
    private final TokenParserCallback<N> parser;
    @Nullable
    private final N previous;

    public UserParserCallbackImpl(int leftBindingPower, @NotNull TokenFinder<N> tokenFinder, @NotNull TokenParserCallback<N> parser, @Nullable final N previous) {
        this.leftBindingPower = leftBindingPower;
        this.tokenFinder = tokenFinder;
        this.parser = parser;
        this.previous = previous;
    }

    @NotNull
    @Override
    public N subExpression(@Nullable N previous) {
        return parser.parseExpression(previous, leftBindingPower);
    }

    @NotNull
    @Override
    public Token<N> expectSingleToken(@NotNull String tokenTypeName) {
        return swallow(tokenTypeName, parser);
    }

    @NotNull
    private Token<N> swallow(@NotNull String tokenTypeName, TokenParserCallback<N> parser) {
        DynamicTokenType<N> type = tokenFinder.getTokenTypeFor(tokenTypeName);
        return parser.swallow(type);
    }

    @Override
    public boolean nextIs(@NotNull String tokenTypeName) {
        DynamicTokenType<N> expectedType = tokenFinder.getTokenTypeFor(tokenTypeName);
        return parser.peek().getType().equals(expectedType);
    }

    @NotNull
    @Override
    public N parseSingleToken(N previous, @NotNull String tokenTypeName) {
        Token<N> token = swallow(tokenTypeName, parser);
        return parser.prefixParse(previous, token);
    }

    @NotNull
    @Override
    public N subExpression() {
        return subExpression(previous);
    }
}
