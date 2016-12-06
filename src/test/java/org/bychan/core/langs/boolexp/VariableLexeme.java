package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VariableLexeme implements Lexeme<BooleanExpressionNode> {
    @NotNull
    private final String name;
    @NotNull
    private final LexingMatch match;

    private VariableLexeme(@NotNull final LexingMatch match) {
        this.match = match;
        this.name = match.getText();
        if (!name.matches("[a-z]+")) {
            throw new IllegalArgumentException("Variable name can only contain lower-case letters, was '" + name + "'");
        }
    }

    @NotNull
    public static VariableLexeme valueOf(@NotNull final LexingMatch match) {
        return new VariableLexeme(match);
    }

    @Nullable
    @Override
    public NudParseAction<BooleanExpressionNode> getNud() {
        return (left, parser) -> new VariableNode(name);
    }

    @Nullable
    @Override
    public LedParseAction<BooleanExpressionNode> getLed() {
        return null;
    }

    public int lbp() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public Token<BooleanExpressionNode> getToken() {
        return VariableToken.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}

