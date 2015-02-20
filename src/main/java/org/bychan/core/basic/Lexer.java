package org.bychan.core.basic;

import org.bychan.core.dynamic.TokenMatchResult;
import org.bychan.core.dynamic.TokenMatcher;
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

    @NotNull
    static <N> EndLexeme<N> makeEndToken(@NotNull String input) {
        return new EndLexeme<>(new LexingMatch<>(input.length(), input.length(), "", EndToken.get()));
    }

    public List<Lexeme<N>> lex(@NotNull final String input) {
        final List<Lexeme<N>> lexemes = new ArrayList<>();
        for (int searchStart = 0; searchStart < input.length(); ) {
            LexingMatch<N> match = findMatch(searchStart, input, lexemes);
            if (match == null) {
                throw new LexingFailedException(getLexingPosition(input, searchStart, lexemes), "No matching rule");
            }
            Lexeme<N> lexeme = match.toLexeme();
            if (lexeme.getToken().keepAfterLexing()) {
                lexemes.add(lexeme);
            }
            int progress = match.getEndPosition() - match.getStartPosition();
            searchStart += progress;
        }
        lexemes.add(makeEndToken(input));
        return lexemes;
    }

    private LexingPosition getLexingPosition(String input, int i, @NotNull final List<Lexeme<N>> lexemes) {
        Lexeme<N> last = lexemes.isEmpty() ? null : lexemes.get(lexemes.size() - 1);
        return new LexingPosition(StringUtils.getTextPosition(input, i), input.substring(i), last);
    }

    @Nullable
    private LexingMatch<N> findMatch(final int searchStart, @NotNull final String input, @NotNull final List<Lexeme<N>> lexemes) {
        for (Token<N> token : tokens) {
            TokenMatcher matcher = token.getMatcher();
            TokenMatchResult result = matcher.tryMatch(input, searchStart);
            if (result != null) {
                int matchEndPosition = result.getMatchEndPosition();
                int progress = matchEndPosition - searchStart;
                if (progress < 1) {
                    throw new LexingFailedException(getLexingPosition(input, searchStart, lexemes),
                            String.format("Match result '%s' for token '%s' matched but did not advance lexing. " +
                                            "Search start position was %d, end was %d",
                                    result, token, searchStart, matchEndPosition));
                }
                String stringMatch = input.substring(searchStart, matchEndPosition);
                return new LexingMatch<>(searchStart, result.getMatchEndPosition(), stringMatch, token, result.getLexerValue());
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
