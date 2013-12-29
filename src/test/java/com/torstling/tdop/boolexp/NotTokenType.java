package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class NotTokenType implements TokenType<BooleanExpressionNode> {

    private static final NotTokenType INSTANCE = new NotTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new NotToken(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\!");
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    public static NotTokenType get() {
        return INSTANCE;
    }
}
