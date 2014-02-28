package com.torstling.tdop.core;

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
    private final List<TokenType<N>> tokenTypes;

    public Lexer(@NotNull final Collection<? extends TokenType<N>> tokenTypes) {
        this.tokenTypes = new ArrayList<>(tokenTypes);
    }

    public List<Token<N>> lex(@NotNull final String input) {
        final List<Token<N>> tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); ) {
            String substring = input.substring(i);
            TokenMatchResult<N> matchResult = findMatchingToken(i, substring);
            if (matchResult == null) {
                throw new LexingFailedException(new LexingPosition(i, substring), "No matching rule for char-range starting at " + i + ": '" + substring + "'");
            }
            Token<N> token = matchResult.getToken();
            if (token.getType().include()) {
                tokens.add(token);
            }
            LexingMatch match = matchResult.getMatch();
            TokenType<N> tokenType = matchResult.getTokenType();
            int progress = match.getEndPosition() - match.getStartPosition();
            if (progress < 1) {
                throw new LexingFailedException(new LexingPosition(i, substring), String.format("Match '%s' for token type '%s' produced token '%s' but did not advance lexing. Aborting.", match, tokenType, token));
            }
            i += progress;
        }
        tokens.add(new EndToken<>(new LexingMatch(input.length(), input.length(), "END")));
        return tokens;
    }

    @Nullable
    private TokenMatchResult<N> findMatchingToken(final int i, @NotNull final String substring) {
        for (TokenType<N> tokenType : tokenTypes) {
            Pattern pattern = tokenType.getPattern();
            Matcher matcher = pattern.matcher(substring);
            if (matcher.lookingAt()) {
                int substringStart = matcher.start();
                int substringEnd = matcher.end();
                String stringMatch = substring.substring(0, substringEnd);
                LexingMatch match = new LexingMatch(substringStart + i, substringEnd + i, stringMatch);
                Token<N> token = tokenType.toToken(match);
                return new TokenMatchResult<N>(match, tokenType, token);
            }
        }
        return null;
    }

    @NotNull
    public LexingResult<N> tryLex(@NotNull final String text) {
        try {
            final List<Token<N>> tokens = lex(text);
            return LexingResult.success(tokens);
        } catch (LexingFailedException e) {
            return LexingResult.failure(new LexingFailedInformation(e.getMessage(), e.getLexingPosition()));
        }
    }
}
