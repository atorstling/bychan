package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OrToken implements Token<BooleanExpressionNode> {
    @NotNull
    private final LexingMatch match;

    public OrToken(@NotNull final LexingMatch match) {
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
        return (previous, parser) -> new OrNode(previous, parser.parseExpression(previous, leftBindingPower()));
    }

    public int leftBindingPower() {
        return 20;
    }


    @Override
    @NotNull
    public TokenType<BooleanExpressionNode> getType() {
        return OrTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
