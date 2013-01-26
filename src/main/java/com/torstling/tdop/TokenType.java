package com.torstling.tdop;

enum TokenType {
    DIGIT("\\d") {
        @Override
        Token toToken(String value) {
            return DigitToken.valueOf(value);
        }
    },
    SUBTRACT("-") {
        @Override
        Token toToken(String value) {
            return new SubtractionToken();
        }
    },
    MULTIPLY("\\*") {
        @Override
        Token toToken(String value) {
            return new MultiplicationToken();
        }
    },
    LEFT_PAREN("\\(") {
        @Override
        Token toToken(String value) {
            return new LeftParenthesisToken();
        }
    },
    RIGHT_PAREN("\\)") {
        @Override
        Token toToken(String value) {
            return new RightParenthesisToken();
        }
    };
    private String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    abstract Token toToken(final String value);

    public String getPattern() {
        return pattern;
    }
}
