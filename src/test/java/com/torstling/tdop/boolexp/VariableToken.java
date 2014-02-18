package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class VariableToken<S> implements Token<BooleanExpressionNode> {
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
    public static <S> VariableToken<S> valueOf(@NotNull final LexingMatch match) {
        return new VariableToken<>(match);
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new VariableNode(name);
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
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

