package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.ArrayDeque;
import java.util.List;

public class CalculatorParserImpl implements TokenParserCallback {

    @NotNull
    private final ArrayDeque<Token> tokens;

    public CalculatorParserImpl(List<? extends Token> tokens) {
        this.tokens = new ArrayDeque<Token>(tokens);
    }

    @NotNull
    public Node parse() {
        return expression(0);
    }

    @NotNull
    public Node expression(int callerInfixBindingPower) {
        //An expression always starts with a symbol which can qualify as a prefix value
        //i.e
        // "+" as in "positive", used in for instance "+3 + 5", parses to +(rest of expression)
        // "-" as in "negative", used in for instance "-3 + 5", parses to -(rest of expression)
        // "(" as in "start subexpression", used in for instance "(3)", parses rest of expression with 0 strength,
        //         which keeps going until next 0-valued token is encountered (")" or end)
        // any digit, used in for instance "3", parses to 3.
        Node currentLeftHandSide = tokens.pop().prefixParse(this);
        //When we have the prefix parsing settled, we cannot be sure that the parsing is done. Digit parsing
        //returns almost immediately for instance. If the prefix parse swallowed all the expression, only the end
        //token will remain. But since the end token has 0 binding power, we will never continue in this case.
        //
        //If there is more to parse, however, we should keep parsing as long as we don't encounter a weaker token
        //than the caller is currently parsing. When a weaker token is encountered, the tokens so far should namely
        //be wrapped up into a part-expression.
        while (tokens.peek().infixBindingPower() > callerInfixBindingPower) {
            //Parsing happens by passing the current LHS to the operator, which will continue parsing.
            currentLeftHandSide = tokens.pop().infixParse(currentLeftHandSide, this);
        }
        return currentLeftHandSide;
    }

    public void swallow(Class<? extends Token> expectedClass) {
        Token next = tokens.pop();
        Class<? extends Token> actualClass = next.getClass();
        if (!actualClass.equals(expectedClass)) {
            throw new IllegalStateException("Expected " + expectedClass.getSimpleName() + ", got " + actualClass.getSimpleName());
        }
    }
}
