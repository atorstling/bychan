package org.bychan.shared;

import org.bychan.core.LexingMatch;
import org.bychan.core.Token;
import org.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class RightParenthesisTokenType<N> implements TokenType<N> {

    private static final RightParenthesisTokenType INSTANCE = new RightParenthesisTokenType<>();

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new RightParenthesisToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\)");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <N> RightParenthesisTokenType<N> get() {
        //noinspection unchecked
        return (RightParenthesisTokenType<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
