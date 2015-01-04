package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.TokenParserCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class UserParserCallbackImpl<N> implements UserParserCallback<N> {
    private final int leftBindingPower;
    @NotNull
    private final DynamicTokenFinder<N> tokenFinder;
    @Nullable
    private TokenParserCallback<N> parser;

    public UserParserCallbackImpl(int leftBindingPower, @NotNull DynamicTokenFinder<N> tokenFinder) {
        this.leftBindingPower = leftBindingPower;
        this.tokenFinder = tokenFinder;
        this.parser = null;
    }

    @NotNull
    @Override
    public N subExpression(@Nullable N previous, int leftBindingPower) {
        return getParser().parseExpression(previous, leftBindingPower);
    }

    @NotNull
    private TokenParserCallback<N> getParser() {
        if (parser == null) {
            throw new IllegalStateException("No parser available");
        }
        return parser;
    }

    public void offer(@NotNull TokenParserCallback<N> parser) {
        if (this.parser == null) {
            this.parser = parser;
        }
    }

    @NotNull
    @Override
    public N subExpression(@Nullable N previous) {
        return getParser().parseExpression(previous, leftBindingPower);
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
        return getParser().peek().getToken().equals(expectedToken);
    }

    @NotNull
    @Override
    public N parseSingleToken(N previous, @NotNull TokenKey tokenKey) {
        Lexeme<N> lexeme = swallow(tokenKey, parser);
        return getParser().nud(previous, lexeme);
    }
}
