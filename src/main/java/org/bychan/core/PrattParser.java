package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrattParser<N> implements TokenParserCallback<N> {

    @NotNull
    private final TokenStack<N> tokenStack;

    public PrattParser(@NotNull List<Token<N>> tokens) {
        this.tokenStack = new TokenStack<>(tokens);
    }

    /**
     * Parse upcoming tokenStack from the stream into an expression, and keep going
     * until token binding powers drop down to or below the supplied floor. If this
     * feels backwards, remember that weak operands end up higher in the parse tree, consider for instance
     * <code>1*2 + 3 </code> which becomes
     * <pre>
     *       +
     *      / \
     *    1*2  3
     * </pre>.
     * To parse this subExpression, we start by swallowing "1*2", stopping by "+". This is achieved by calling
     * this method with the lower binding power of "+" as an argument.
     */
    @NotNull
    public N parseExpression(@Nullable N previous, final int powerFloor) {
        // An subExpression always starts with a symbol which can qualify as a prefix value
        // i.e
        // "+" as in "positive", used in for instance "+3 + 5", parses to +(rest of subExpression)
        // "-" as in "negative", used in for instance "-3 + 5", parses to -(rest of subExpression)
        // "(" as in "start sub-subExpression", used in for instance "(3)", parses rest of subExpression with 0 strength,
        //         which keeps going until next 0-valued token is encountered (")" or end)
        // any digit, used in for instance "3", parses to 3.
        Token<N> firstToken = pop();
        if (!firstToken.supportsPrefixParsing()) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Current token does not support prefix parsing", getParsingPosition()));
        }
        final N first = firstToken.prefixParse(previous, this);
        // When we have the prefix parsing settled, we cannot be sure that the parsing is done. Digit parsing
        // returns almost immediately for instance. If the prefix parse swallowed all the subExpression, only the end
        // token will remain. But since the end token has 0 binding power, we will never continue in this case.
        //
        // If there is more to parse, however, we should keep parsing as long as we encounter stronger tokenStack
        // than the caller is currently parsing. When a weaker token is encountered, the tokenStack so far should namely
        // be wrapped up into a part-subExpression. This part-subExpression will then usually form the LHS or RHS of
        // the weaker operand. Remember that weak operands end up higher in the tree, consider for instance
        // 1*2 + 3 which becomes
        //
        //       +
        //      / \
        //    1*2 3
        //
        // The multiplication token will here call parse on the rest of the subExpression ("2+3"). This parse
        // should abort as soon as the weaker addition token is encountered, so that it returns "2" as the RHS
        // of the multiplication.
        // The addition operators infix-parser is then called by the top-level subExpression parser,
        // passing (1*2) into it as the subExpression parsed so far. It will then proceed to swallow the 3,
        // completing the subExpression.
        return parseLoop(first, powerFloor);
    }

    private N parseLoop(@NotNull final N currentLeftHandSide, final int powerFloor) {
        Token<N> peekedToken = peek();
        if (peekedToken.leftBindingPower() > powerFloor) {
            //Parsing happens by passing the previous LHS to the operator, which will continue parsing.
            Token<N> takenToken = pop();
            if (!takenToken.supportsInfixParsing()) {
                throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Current token does not support infix parsing", getParsingPosition()));
            }
            N nextExpression = takenToken.infixParse(currentLeftHandSide, this);
            return parseLoop(nextExpression, powerFloor);
        }
        return currentLeftHandSide;
    }

    @NotNull
    public Token<N> swallow(@NotNull TokenType<N> type) {
        Token<N> next = tokenStack.pop();
        if (!next.getType().equals(type)) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Expected a token of type '" + type + "', but got '" + next + "'", getParsingPosition()));
        }
        return next;
    }

    @NotNull
    public ParsingPosition getParsingPosition() {
        Token<N> previous = tokenStack.previous();
        final int startPosition;
        if (previous == null) {
            startPosition = 0;
        } else {
            LexingMatch match = previous.getMatch();
            startPosition = match.getStartPosition();
        }
        return new ParsingPosition(startPosition, tokenStack);
    }

    @NotNull
    @Override
    public Token<N> peek() {
        return tokenStack.peek();
    }

    @NotNull
    @Override
    public Token<N> pop() {
        return tokenStack.pop();
    }
}
