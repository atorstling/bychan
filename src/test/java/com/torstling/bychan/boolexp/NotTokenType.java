package com.torstling.bychan.boolexp;

import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class NotTokenType<S> implements TokenType<BooleanExpressionNode> {

    private static final NotTokenType INSTANCE = new NotTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new NotToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("!");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <S> NotTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
