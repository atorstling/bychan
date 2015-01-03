package org.bychan.core.langs.shared;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class LeftParenthesisToken<N> implements Token<N> {

    @NotNull
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        return new LeftParenthesisLexeme<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\(");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <N> LeftParenthesisToken<N> get() {
        return new LeftParenthesisToken<>();
    }
}
