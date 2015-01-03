package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OrLexeme implements Lexeme<BooleanExpressionNode> {
    @NotNull
    private final LexingMatch match;

    public OrLexeme(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public NudParseAction<BooleanExpressionNode> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public LedParseAction<BooleanExpressionNode> getInfixParser() {
        return (previous, parser) -> new OrNode(previous, parser.parseExpression(previous, leftBindingPower()));
    }

    public int leftBindingPower() {
        return 20;
    }


    @Override
    @NotNull
    public Token<BooleanExpressionNode> getToken() {
        return OrToken.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
