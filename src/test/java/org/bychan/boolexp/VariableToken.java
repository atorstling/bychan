package org.bychan.boolexp;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VariableToken implements Token<BooleanExpressionNode> {
    @NotNull
    private final String name;
    @NotNull
    private final LexingMatch match;

    private VariableToken(@NotNull final LexingMatch match) {
        this.match = match;
        this.name = match.getText();
        if (!name.matches("[a-z]+")) {
            throw new IllegalArgumentException("Variable name can only contain lower-case letters, was '" + name + "'");
        }
    }

    @NotNull
    public static <S> VariableToken valueOf(@NotNull final LexingMatch match) {
        return new VariableToken(match);
    }

    @Nullable
    @Override
    public PrefixParseAction<BooleanExpressionNode> getPrefixParser() {
        return (previous, parser) -> new VariableNode(name);
    }

    @Nullable
    @Override
    public InfixParseAction<BooleanExpressionNode> getInfixParser() {
        return null;
    }

    public int leftBindingPower() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public TokenType<BooleanExpressionNode> getType() {
        return VariableTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}

