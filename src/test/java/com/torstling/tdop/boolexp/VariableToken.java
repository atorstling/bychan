package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

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
    public static VariableToken valueOf(@NotNull final LexingMatch match) {
        return new VariableToken(match);
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

