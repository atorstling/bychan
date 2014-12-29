package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotToken implements Token<BooleanExpressionNode> {

    @NotNull
    private final LexingMatch match;

    public NotToken(@NotNull LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public PrefixParseAction<BooleanExpressionNode> getPrefixParser() {
        return (previous, parser) -> new NotNode(parser.parseExpression(previous, leftBindingPower()));
    }

    @Nullable
    @Override
    public InfixParseAction<BooleanExpressionNode> getInfixParser() {
        return null;
    }

    public int leftBindingPower() {
        return 1;
    }

    @Override
    @NotNull
    public TokenType<BooleanExpressionNode> getType() {
        return NotTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
