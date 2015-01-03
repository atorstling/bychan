package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.TokenParserCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class UserParserCallbackImpl<N> implements UserParserCallback<N> {
    private final int leftBindingPower;
    @NotNull
    private final DynamicTokenFinder<N> tokenFinder;
    @NotNull
    private final TokenParserCallback<N> parser;
    @Nullable
    private final N previous;

    public UserParserCallbackImpl(int leftBindingPower, @NotNull DynamicTokenFinder<N> tokenFinder, @NotNull TokenParserCallback<N> parser, @Nullable final N previous) {
        this.leftBindingPower = leftBindingPower;
        this.tokenFinder = tokenFinder;
        this.parser = parser;
        this.previous = previous;
    }

    @NotNull
    @Override
    public N subExpression(@Nullable N previous, int leftBindingPower) {
        return parser.parseExpression(previous, leftBindingPower);
    }

    @NotNull
    @Override
    public N subExpression(@Nullable N previous) {
        return parser.parseExpression(previous, leftBindingPower);
    }

    @NotNull
    @Override
    public Lexeme<N> expectSingleLexeme(@NotNull TokenKey tokenKey) {
        return swallow(tokenKey, parser);
    }

    @NotNull
    private Lexeme<N> swallow(@NotNull TokenKey tokenKey, TokenParserCallback<N> parser) {
        DynamicToken<N> token = tokenFinder.getToken(tokenKey);
        return parser.swallow(token);
    }

    @Override
    public boolean nextIs(@NotNull TokenKey tokenKey) {
        DynamicToken<N> expectedToken = tokenFinder.getToken(tokenKey);
        return parser.peek().getToken().equals(expectedToken);
    }

    @NotNull
    @Override
    public N parseSingleToken(N previous, @NotNull TokenKey tokenKey) {
        Lexeme<N> lexeme = swallow(tokenKey, parser);
        return parser.nud(previous, lexeme);
    }

    @NotNull
    @Override
    public N subExpression() {
        return subExpression(previous);
    }
}
