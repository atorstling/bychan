package com.torstling.bychan;

import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Created by alext on 2014-11-29.
 */
class MockTokenType implements TokenType<Object> {
    @NotNull
    @Override
    public Token<Object> toToken(@NotNull LexingMatch match) {
        throw new IllegalStateException();
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        throw new IllegalStateException();
    }

    @Override
    public boolean include() {
        throw new IllegalStateException();
    }
}
