package org.bychan.core.basic;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The result of a lexing match.
 * <p>
 * When the lexer finds a piece of the input text which matches a particular {@link Token} it
 * makes a {@link org.bychan.core.basic.LexingMatch} to describe the match: The matching text fragment, it's location and the
 * the originating {@link Token}.
 * </p>
 */
public class LexingMatch<N> {

    private final int startPosition;
    private final int endPosition;
    @NotNull
    private final String text;
    @NotNull
    private final Token<N> token;
    @Nullable
    private final Object lexerResult;

    public LexingMatch(int startPosition, int endPosition, @NotNull final String text, @NotNull final Token<N> token) {
        this(startPosition, endPosition, text, token, null);
    }

    public LexingMatch(int startPosition, int endPosition, @NotNull final String text, @NotNull final Token<N> token, @Nullable Object lexerResult) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.text = text;
        this.token = token;
        this.lexerResult = lexerResult;
    }

    @NotNull
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "LexingMatch{" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", text='" + text + '\'' +
                '}';
    }

    public int getEndPosition() {
        return endPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingMatch that = (LexingMatch) o;

        return endPosition == that.endPosition && startPosition == that.startPosition && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = startPosition;
        result = 31 * result + endPosition;
        result = 31 * result + text.hashCode();
        return result;
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return token.getMatcher();
    }

    @NotNull
    public String group(int i) {
        return ((RegexMatcher) getMatcher()).group(i, text);
    }

    @NotNull
    public Lexeme<N> toLexeme() {
        return token.toLexeme(this);
    }
}
