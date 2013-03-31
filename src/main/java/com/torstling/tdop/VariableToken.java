package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class VariableToken implements Token<BooleanExpressionNode> {
    @NotNull
    private final String name;
    @NotNull
    private final LexingMatch match;

    public VariableToken(@NotNull final LexingMatch match) {
        this.match = match;
        this.name = match.getText();
        if (!name.matches("[a-z]+")) {
            throw new IllegalArgumentException("Variable name can only contain lower-case letters, was '" + name + "'");
        }
    }

    @NotNull
    public static VariableToken valueOf(@NotNull final LexingMatch match) {
        return new VariableToken(match);
    }

    public String getName() {
        return name;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        return new VariableNode(name);
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode left, @NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOfType(@NotNull final TokenType<BooleanExpressionNode> type) {
        return type.equals(VariableTokenType.get());
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}

