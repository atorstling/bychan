package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.List;

public class PrattParser<N extends Node> implements TokenParserCallback<N> {

    @NotNull
    private final ArrayDeque<Token<N>> tokens;

    public PrattParser(List<? extends Token<N>> tokens) {
        this.tokens = new ArrayDeque<>(tokens);
    }

    @NotNull
    public N parse() {
        return expression(0);
    }

    @NotNull
    public N expression(int continueWhileTokensHavePowerAbove) {
        // An expression always starts with a symbol which can qualify as a prefix value
        // i.e
        // "+" as in "positive", used in for instance "+3 + 5", parses to +(rest of expression)
        // "-" as in "negative", used in for instance "-3 + 5", parses to -(rest of expression)
        // "(" as in "start subexpression", used in for instance "(3)", parses rest of expression with 0 strength,
        //         which keeps going until next 0-valued token is encountered (")" or end)
        // any digit, used in for instance "3", parses to 3.
        N currentLeftHandSide = tokens.pop().prefixParse(this);
        // When we have the prefix parsing settled, we cannot be sure that the parsing is done. Digit parsing
        // returns almost immediately for instance. If the prefix parse swallowed all the expression, only the end
        // token will remain. But since the end token has 0 binding power, we will never continue in this case.
        //
        // If there is more to parse, however, we should keep parsing as long as we encounter stronger tokens
        // than the caller is currently parsing. When a weaker token is encountered, the tokens so far should namely
        // be wrapped up into a part-expression. This part-expression will then usually form the LHS or RHS of
        // the weaker operand. Remember that weak operands end up higher in the tree, consider for instance
        // 1*2 + 3 which becomes
        //
        //       +
        //      / \
        //    1*2 3
        //
        // The multiplication token will here call parse on the rest of the expression ("2+3"). This parse
        // should abort as soon as the weaker addition token is encountered, so that it returns "2" as the RHS
        // of the multiplication.
        // The addition operators infix-parser is then called by the top-level expression parser,
        // passing (1*2) into it as the expression parsed so far. It will then proceed to swallow the 3,
        // completing the expression.
        while (tokens.peek().infixBindingPower() > continueWhileTokensHavePowerAbove) {
            //Parsing happens by passing the current LHS to the operator, which will continue parsing.
            currentLeftHandSide = tokens.pop().infixParse(currentLeftHandSide, this);
        }
        return currentLeftHandSide;
    }

    @NotNull
    public Token<N> swallow(@NotNull TokenType<N> type) {
        Token<N> next = tokens.pop();
        if (!next.isOfType(type)) {
            throw new IllegalStateException("Expected a token of type " + type + ", but got '" + next + "'");
        }
        return next;
    }
}
