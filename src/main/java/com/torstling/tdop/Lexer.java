package com.torstling.tdop;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private final Pattern pattern;
    private final List<TokenType> tokenTypes;

    public Lexer(@NotNull final List<TokenType> tokenTypes) {
        this.tokenTypes = tokenTypes;
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

    public List<Token> lex(@NotNull final String input) {
        final List<Token> tokens = new ArrayList<Token>();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            tokens.add(findMatchingToken(matcher));
        }
        tokens.add(new EndToken());
        return tokens;
    }


    private Token findMatchingToken(Matcher matcher) {
        for (int group = 1; group <= matcher.groupCount(); group++) {
            String groupValue = matcher.group(group);
            if (groupValue != null) {
                TokenType correspondingTokenType = tokenTypes.get(group - 1);
                return correspondingTokenType.toToken(groupValue);
            }
        }
        return null;
    }
}
