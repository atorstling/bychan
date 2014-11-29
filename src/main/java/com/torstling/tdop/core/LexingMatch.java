package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

/**
 * A lexing match :)
 */
public class LexingMatch<N> {

    private final int startPosition;
    private final int endPosition;
    @NotNull
    private final String text;
    @NotNull
    private final TokenType<N> tokenType;

    public LexingMatch(int startPosition, int endPosition, @NotNull final String text, @NotNull final TokenType<N> tokenType) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.text = text;
        this.tokenType = tokenType;
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
    public ParsingPosition toParsingPosition() {
        return new ParsingPosition(endPosition, text);
    }

    @NotNull
    public String group(int i) {
        Matcher matcher = tokenType.getPattern().matcher(text);
        boolean matches = matcher.matches();
        if (!matches) {
            throw new IllegalStateException();
        }
        return matcher.group(i);
    }
}
