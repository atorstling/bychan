package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

/**
 * A regex sub-pattern-based lexer
 */
public class Lexer<N extends AstNode, S> {
    private final List<TokenType<N, S>> tokenTypes;

    public Lexer(@NotNull final Collection<? extends TokenType<N, S>> tokenTypes) {
        this.tokenTypes = new ArrayList<>(tokenTypes);
    }

    public List<Token<N, S>> lex(@NotNull final String input) {
        final List<Token<N, S>> tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); ) {
            String substring = input.substring(i);
            Token<N, S> token = findMatchingToken2(i, substring);
            if (token == null) {
                throw new LexingFailedException("No matching rule for char-range starting at " + i + ": '" + substring + "'");
            }
            if (token.getType().include()) {
                tokens.add(token);
            }
            LexingMatch match = token.getMatch();
            i += (match.getEndPosition() - match.getStartPosition());
        }
        tokens.add(new EndToken<>(new LexingMatch(input.length(), input.length(), "END")));
        return tokens;
    }

    @Nullable
    private Token<N, S> findMatchingToken2(final int i, @NotNull final String substring) {
        for (TokenType<N, S> tokenType : tokenTypes) {
            Matcher matcher = tokenType.getPattern().matcher(substring);
            if (matcher.lookingAt()) {
                int substringStart = matcher.start();
                int substringEnd = matcher.end();
                String match = substring.substring(0, substringEnd);
                return tokenType.toToken(new LexingMatch(substringStart + i, substringEnd + i, match));
            }
        }
        return null;
    }
}
