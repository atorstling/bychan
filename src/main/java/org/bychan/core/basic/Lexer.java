package org.bychan.core.basic;

import org.bychan.core.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A regex sub-pattern-based lexer
 */
public class Lexer<N> {
    private final List<Token<N>> tokens;

    public Lexer(@NotNull final Collection<? extends Token<N>> tokens) {
        this.tokens = new ArrayList<>(tokens);
    }

    public List<Lexeme<N>> lex(@NotNull final String input) {
        final List<Lexeme<N>> lexemes = new ArrayList<>();
        for (int i = 0; i < input.length(); ) {
            String substring = input.substring(i);
            Lexeme<N> lexeme = findMatchingToken(i, substring);
            if (lexeme == null) {
                throw new LexingFailedException(getLexingPosition(input, i, substring), "No matching rule");
            }
            if (lexeme.getToken().include()) {
                lexemes.add(lexeme);
            }
            Token<N> token = lexeme.getToken();
            LexingMatch match = lexeme.getMatch();
            int progress = match.getEndPosition() - match.getStartPosition();
            if (progress < 1) {
                throw new LexingFailedException(getLexingPosition(input, i, substring), String.format("Match '%s' for lexeme type '%s' produced lexeme '%s' but did not advance lexing. Aborting.", match, token, lexeme));
            }
            i += progress;
        }
        lexemes.add(makeEndToken(input));
        return lexemes;
    }

    private LexingPosition getLexingPosition(String input, int i, String substring) {
        return new LexingPosition(StringUtils.getTextPosition(input, i), substring);
    }

    @NotNull
    static <N> EndLexeme<N> makeEndToken(@NotNull String input) {
        return new EndLexeme<>(new LexingMatch<>(input.length(), input.length(), "", EndToken.get()));
    }

    @Nullable
    private Lexeme<N> findMatchingToken(final int i, @NotNull final String substring) {
        for (Token<N> token : tokens) {
            Pattern pattern = token.getPattern();
            Matcher matcher = pattern.matcher(substring);
            if (matcher.lookingAt()) {
                int substringStart = matcher.start();
                int substringEnd = matcher.end();
                String stringMatch = substring.substring(0, substringEnd);
                LexingMatch<N> match = new LexingMatch<>(substringStart + i, substringEnd + i, stringMatch, token);
                return match.toLexeme();
            }
        }
        return null;
    }

    @NotNull
    public LexingResult<N> tryLex(@NotNull final String text) {
        try {
            final List<Lexeme<N>> lexemes = lex(text);
            return LexingResult.success(lexemes);
        } catch (LexingFailedException e) {
            return LexingResult.failure(new LexingFailedInformation(e.getMessage(), e.getLexingPosition()));
        }
    }
}
