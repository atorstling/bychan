package com.torstling.tdop;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    public ArrayList<Token> lex(String input) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        Pattern pattern = Pattern.compile("\\s*(?:(\\d+)|([-+*/])|([\\(\\)]))");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String digit = matcher.group(1);
            String operator = matcher.group(2);
            String parenthesis = matcher.group(3);
            if (digit != null) {
                assert operator == null;
                tokens.add(DigitToken.valueOf(digit));
            } else if (operator != null) {
                if (operator.equals("-")) {
                    tokens.add(new SubtractionToken());
                } else {
                    throw new IllegalStateException("Unknown operator");
                }
            } else if (parenthesis != null) {
                if (parenthesis.equals("(")) {
                    tokens.add(new LeftParenthesisToken());
                } else if (parenthesis.equals(")")) {
                    tokens.add(new RightParenthesisToken());
                }
            }
        }
        tokens.add(new EndToken());
        return tokens;
    }
}
