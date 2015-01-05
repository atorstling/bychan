package org.bychan.core.langs.shared;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class WhitespaceToken<N> implements Token<N> {
    private final String pattern;

    public WhitespaceToken() {
        this.pattern = "\\s+";
    }

    @NotNull
    @Override
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        return new WhitespaceLexeme<>(this, match);
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public boolean keepAfterLexing() {
        return false;
    }
}
