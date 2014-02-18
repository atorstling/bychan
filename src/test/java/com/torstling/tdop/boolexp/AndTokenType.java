package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class AndTokenType<S> implements TokenType<BooleanExpressionNode> {

    private static final AndTokenType INSTANCE = new AndTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new AndToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\*");
    }

    @Override
    public boolean include() {
        return true;
    }

    @NotNull
    public static <S> AndTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
