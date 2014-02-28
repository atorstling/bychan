package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExpressionStatement<N> implements Statement<N> {
    private final int powerFloor;

    public ExpressionStatement(int powerFloor) {
        this.powerFloor = powerFloor;
    }

    /**
     * Parse upcoming tokens from the stream into an expression, and keep going
     * until token binding powers drop down to or below the supplied floor. If this
     * feels backwards, remember that weak operands end up higher in the parse tree, consider for instance
     * <code>1*2 + 3 </code> which becomes
     * <pre>
     *       +
     *      / \
     *    1*2  3
     * </pre>.
     * To parse this expression, we start by swallowing "1*2", stopping by "+". This is achieved by calling
     * this method with the lower binding power of "+" as an argument.
     */
    @Override
    @NotNull
    public N parse(@Nullable N previous, @NotNull PrattParser<N> parser) {
        // An expression always starts with a symbol which can qualify as a prefix value
        // i.e
        // "+" as in "positive", used in for instance "+3 + 5", parses to +(rest of expression)
        // "-" as in "negative", used in for instance "-3 + 5", parses to -(rest of expression)
        // "(" as in "start sub-expression", used in for instance "(3)", parses rest of expression with 0 strength,
        //         which keeps going until next 0-valued token is encountered (")" or end)
        // any digit, used in for instance "3", parses to 3.
        Token<N> firstToken = parser.pop();
        final N first = firstToken.prefixParse(previous, parser);
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
        return parseLoop(first, powerFloor, parser);
    }

    private N parseLoop(@NotNull final N currentLeftHandSide, final int powerFloor, PrattParser<N> parser) {
        Token<N> peekedToken = parser.peek();
        if (peekedToken.infixBindingPower() > powerFloor) {
            //Parsing happens by passing the current LHS to the operator, which will continue parsing.
            Token<N> takenToken = parser.pop();
            N nextExpression = takenToken.infixParse(currentLeftHandSide, parser);
            return parseLoop(nextExpression, powerFloor, parser);
        }
        return currentLeftHandSide;
    }
}
