package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotLexeme implements Lexeme<BooleanExpressionNode> {

    @NotNull
    private final LexingMatch match;

    public NotLexeme(@NotNull LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public NudParseAction<BooleanExpressionNode> getNud() {
        return (left, parser) -> new NotNode(parser.expression(left, leftBindingPower()));
    }

    @Nullable
    @Override
    public LedParseAction<BooleanExpressionNode> getLed() {
        return null;
    }

    public int leftBindingPower() {
        return 1;
    }

    @Override
    @NotNull
    public Token<BooleanExpressionNode> getToken() {
        return NotToken.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
