package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class OrTokenType<S> implements TokenType<BooleanExpressionNode, S> {
    private static final OrTokenType INSTANCE = new OrTokenType();

    @NotNull
    public Token<BooleanExpressionNode, S> toToken(@NotNull LexingMatch match) {
        return new OrToken(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\+");
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    @NotNull
    public static OrTokenType get() {
        return INSTANCE;
    }
}
