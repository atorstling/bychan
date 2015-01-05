package org.bychan.core.langs.shared;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class RightParenthesisToken<N> implements Token<N> {

    private static final RightParenthesisToken INSTANCE = new RightParenthesisToken<>();

    @NotNull
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        return new RightParenthesisLexeme<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\)");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public static <N> RightParenthesisToken<N> get() {
        //noinspection unchecked
        return (RightParenthesisToken<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
