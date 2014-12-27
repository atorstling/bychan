package org.bychan.core.langs.shared;

import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class LeftParenthesisTokenType<N> implements TokenType<N> {

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new LeftParenthesisToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\(");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <N> LeftParenthesisTokenType<N> get() {
        return new LeftParenthesisTokenType<>();
    }
}
