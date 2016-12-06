package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AndLexeme implements Lexeme<BooleanExpressionNode> {
    private final LexingMatch match;

    public AndLexeme(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public NudParseAction<BooleanExpressionNode> getNud() {
        return null;
    }

    @Nullable
    @Override
    public LedParseAction<BooleanExpressionNode> getLed() {
        return (left, parser) -> {
            BooleanExpressionNode right = parser.expr(left, lbp());
            return new AndNode(left, right);
        };
    }

    public int lbp() {
        return 10;
    }

    @Override
    @NotNull
    public Token<BooleanExpressionNode> getToken() {
        return AndToken.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}

