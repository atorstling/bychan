package com.torstling.bychan.boolexp;

import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class OrTokenType<S> implements TokenType<BooleanExpressionNode> {
    private static final OrTokenType INSTANCE = new OrTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new OrToken(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\+");
    }

    @Override
    public boolean include() {
        return true;
    }

    @NotNull
    public static <S> OrTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
