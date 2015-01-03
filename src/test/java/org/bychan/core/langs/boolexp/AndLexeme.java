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
    public PrefixParseAction<BooleanExpressionNode> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public InfixParseAction<BooleanExpressionNode> getInfixParser() {
        return (previous, parser) -> {
            BooleanExpressionNode right = parser.parseExpression(previous, leftBindingPower());
            return new AndNode(previous, right);
        };
    }

    public int leftBindingPower() {
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

