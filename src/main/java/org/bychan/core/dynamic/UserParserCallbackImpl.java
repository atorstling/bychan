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
    private final TokenDefinition<N> def;
    @Nullable
    private final N left;
    @NotNull
    private final DynamicLexeme<N> lexeme;
    @NotNull
    private TokenParserCallback<N> parser;

    public UserParserCallbackImpl(TokenDefinition<N> def, @NotNull DynamicTokenFinder<N> tokenFinder, @NotNull final TokenParserCallback<N> parser, @Nullable N left, @NotNull final DynamicLexeme<N> lexeme) {
        this.def = def;
        this.left = left;
        this.lexeme = lexeme;
        this.leftBindingPower = def.getLeftBindingPower();
        this.tokenFinder = tokenFinder;
        this.parser = parser;
    }


    @NotNull
    @Override
    public N expression(@Nullable N left, int leftBindingPower) {
        return parser.parseExpression(left, leftBindingPower);
    }

    @NotNull
    @Override
    public N expression(@Nullable N left) {
        return parser.parseExpression(left, leftBindingPower);
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
    public N parseSingleToken(N left, @NotNull TokenKey tokenKey) {
        Lexeme<N> lexeme = swallow(tokenKey, parser);
        return parser.nud(left, lexeme);
    }

    @Override
    public N nud() {
        return parser.nud(left, lexeme);
    }
}
