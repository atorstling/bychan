package com.torstling.tdop.core;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A lexer
 */
public class Lexer<N extends AstNode> {
    private final Pattern pattern;
    private final List<? extends TokenType<N>> tokenTypes;

    public Lexer(@NotNull final Collection<? extends TokenType<N>> tokenTypes) {
        this.tokenTypes = new ArrayList<>(tokenTypes);
        Collection<String> subPatterns = makeSubPatterns();
        String patternString = "\\s*(?:" + Joiner.on("|").join(subPatterns) + ")";
        pattern = Pattern.compile(patternString);
    }

    private Collection<String> makeSubPatterns() {
        return Collections2.transform(tokenTypes, new Function<TokenType, String>() {
            public String apply(TokenType tokenType) {
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
            Token<N> matchingToken = findMatchingToken(matcher);
            if (currentStart > lastEnd) {
                final String missedText = input.substring(lastEnd, currentStart);
                throw new RuntimeException("No matching rule for range from " + lastEnd + " to " + currentStart + ": '" + missedText + "'");
            }
            tokens.add(matchingToken);
            lastEnd = matcher.end();
        }
        tokens.add(new EndToken<N>());
        return tokens;
    }


    private Token<N> findMatchingToken(Matcher matcher) {
        for (int group = 1; group <= matcher.groupCount(); group++) {
            String groupValue = matcher.group(group);
            if (groupValue != null) {
                TokenType<N> correspondingTokenType = tokenTypes.get(group - 1);
                return correspondingTokenType.toToken(new LexingMatch(groupValue));
            }
        }
        return null;
    }
}
