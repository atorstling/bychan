package org.bychan.core.basic;

import org.bychan.core.TokenMatcher;
import org.bychan.core.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        for (int searchStart = 0; searchStart < input.length(); ) {
            LexingMatch<N> match = findMatch(searchStart, input);
            if (match == null) {
                throw new LexingFailedException(getLexingPosition(input, searchStart), "No matching rule");
            }
            Lexeme<N> lexeme = match.toLexeme();
            if (lexeme.getToken().keepAfterLexing()) {
                lexemes.add(lexeme);
            }
            Token<N> token = lexeme.getToken();
            int progress = match.getEndPosition() - match.getStartPosition();
            if (progress < 1) {
                throw new LexingFailedException(getLexingPosition(input, searchStart), String.format("Match '%s' for lexeme type '%s' produced lexeme '%s' but did not advance lexing. Aborting.", match, token, lexeme));
            }
            searchStart += progress;
        }
        lexemes.add(makeEndToken(input));
        return lexemes;
    }

    private LexingPosition getLexingPosition(String input, int i) {
        return new LexingPosition(StringUtils.getTextPosition(input, i), input.substring(i));
    }

    @NotNull
    static <N> EndLexeme<N> makeEndToken(@NotNull String input) {
        return new EndLexeme<>(new LexingMatch<>(input.length(), input.length(), "", EndToken.get()));
    }

    @Nullable
    private LexingMatch<N> findMatch(final int searchStart, @NotNull final String input) {
        for (Token<N> token : tokens) {
            TokenMatcher matcher = token.getMatcher();
            int matchEnd = matcher.tryMatch(input, searchStart);
            if (matchEnd != -1) {
                String stringMatch = input.substring(searchStart, matchEnd);
                return new LexingMatch<>(searchStart, matchEnd, stringMatch, token);
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
