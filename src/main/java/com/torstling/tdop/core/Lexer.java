package com.torstling.tdop.core;

import com.torstling.tdop.utils.CollectionUtils;
import com.torstling.tdop.utils.Function;
import com.torstling.tdop.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A regex sub-pattern-based lexer
 */
public class Lexer<N extends AstNode> {
    private final Pattern pattern;
    private final List<TokenType<N>> tokenTypes;

    public Lexer(@NotNull final Collection<? extends TokenType<N>> tokenTypes) {
        this.tokenTypes = new ArrayList<>(tokenTypes);
        Collection<String> allPatterns = makeSubPatterns(this.tokenTypes);
        String includedPattern = "(?:" + StringUtils.join(allPatterns, "|") + ")";
        pattern = Pattern.compile(includedPattern);
    }

    private Collection<String> makeSubPatterns(Collection<TokenType<N>> tokenTypes) {
        return CollectionUtils.transform(tokenTypes, new Function<TokenType, String>() {
            @NotNull
            public String apply(@NotNull TokenType tokenType) {
                return "(" + tokenType.getPattern() + ")";
            }
        });
    }

    public List<Token<N>> lex(@NotNull final String input) {
        final List<Token<N>> tokens = new ArrayList<>();
        Matcher matcher = pattern.matcher(input);
        int lastEnd = 0;
        while (matcher.find()) {
            int currentStart = matcher.start();
            checkMiss(input, currentStart, lastEnd);
            Token<N> matchingToken = findMatchingToken(matcher);
            if (!matchingToken.getType().shouldSkip()) {
                tokens.add(matchingToken);
            }
            lastEnd = matcher.end();
        }
        checkMiss(input, input.length(), lastEnd);
        tokens.add(new EndToken<N>(new LexingMatch(input.length(), input.length(), "END")));
        return tokens;
    }

    private void checkMiss(@NotNull final String input, int currentStart, int lastEnd) {
        if (currentStart > lastEnd) {
            final String missedText = input.substring(lastEnd, currentStart);
            throw new LexingFailedException("No matching rule for char-range from " + lastEnd + " to " + currentStart + ": '" + missedText + "'");
        }
    }


    @NotNull
    private Token<N> findMatchingToken(@NotNull final Matcher matcher) {
        for (int group = 1; group <= matcher.groupCount(); group++) {
            String groupValue = matcher.group(group);
            if (groupValue != null) {
                TokenType<N> correspondingTokenType = tokenTypes.get(group - 1);
                int start = matcher.start();
                int end = matcher.end();
                return correspondingTokenType.toToken(new LexingMatch(start, end, groupValue));
            }
        }
        throw new IllegalStateException("Got parsing match but could not locate corresponding token.");
    }
}
