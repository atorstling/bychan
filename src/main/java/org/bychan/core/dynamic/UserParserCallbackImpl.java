package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenParserCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class UserParserCallbackImpl<N> implements UserParserCallback<N> {
    @Nullable
    private final N left;
    @NotNull
    private final DynamicLexeme<N> lexeme;
    @NotNull
    private final TokenParserCallback<N> parser;

    public UserParserCallbackImpl(@NotNull final TokenParserCallback<N> parser, @Nullable N left, @NotNull final DynamicLexeme<N> lexeme) {
        this.left = left;
        this.lexeme = lexeme;
        this.parser = parser;
    }


    @NotNull
    @Override
    public N parseExpression(@Nullable N left, int leftBindingPower) {
        return parser.parseExpression(left, leftBindingPower);
    }

    @Override
    public boolean peekIsA(Token<N> token) {
        return parser.peek().isA(token);
    }

    @NotNull
    @Override
    public N parseSingleToken(N left, Token<N> token) {
        return parser.nud(left, parser.swallow(token));
    }

    @Override
    public N nud() {
        return parser.nud(left, lexeme);
    }

    @Override
    public <S> S abort(@NotNull String message) {
        return parser.abort(message);
    }

    @NotNull
    @Override
    public Lexeme<N> next() {
        return parser.next();
    }

    @Override
    public Lexeme<N> peek() {
        return parser.peek();
    }

    @Override
    public Lexeme<N> swallow(@NotNull final Token<N> token) {
        return parser.swallow(token);
    }
}
