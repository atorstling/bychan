package org.bychan.core.basic;

import org.bychan.core.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrattParser<N> implements TokenParserCallback<N> {

    @NotNull
    private final TokenStack<N> tokenStack;
    @NotNull
    private String originalInputString;

    public PrattParser(@NotNull List<Token<N>> tokens, @NotNull String originalInputString) {
        this.originalInputString = originalInputString;
        this.tokenStack = new TokenStack<>(tokens);
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
    @NotNull
    public N parseExpression(@Nullable N previous, final int powerFloor) {
        // An expression always starts with a symbol which can qualify as a prefix value
        // i.e
        // "+" as in "positive", used in for instance "+3 + 5", parses to +(rest of expression)
        // "-" as in "negative", used in for instance "-3 + 5", parses to -(rest of expression)
        // "(" as in "start sub-expression", used in for instance "(3)", parses rest of expression with 0 strength,
        //         which keeps going until next 0-valued token is encountered (")" or end)
        // any digit, used in for instance "3", parses to 3.
        Token<N> firstToken = pop();
        final N first = prefixParse(previous, firstToken);
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
        return parseLoop(first, powerFloor);
    }

    private N parseLoop(@NotNull final N currentLeftHandSide, final int powerFloor) {
        Token<N> peekedToken = peek();
        if (peekedToken.leftBindingPower() > powerFloor) {
            //Parsing happens by passing the previous LHS to the operator, which will continue parsing.
            Token<N> takenToken = pop();
            N nextExpression = infixParse(currentLeftHandSide, takenToken);
            return parseLoop(nextExpression, powerFloor);
        }
        return currentLeftHandSide;
    }

    private N infixParse(@NotNull N currentLeftHandSide, @NotNull Token<N> takenToken) {
        InfixParseAction<N> infixParseAction = takenToken.getInfixParser();
        if (infixParseAction == null) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Current token does not support infix parsing", getParsingPosition()));
        }
        return infixParseAction.parse(currentLeftHandSide, this);
    }

    @NotNull
    public Token<N> swallow(@NotNull TokenType<N> type) {
        Token<N> next = tokenStack.pop();
        if (!next.getType().equals(type)) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Expected a token of type '" + type.getName() + "', but got '" + next + "'", getParsingPosition()));
        }
        return next;
    }

    @NotNull
    public ParsingPosition getParsingPosition() {
        Token<N> previous = tokenStack.previous();
        final int startPosition;
        if (previous == null) {
            startPosition = 0;
        } else if (previous.getType().equals(EndTokenType.get())) {
            //Since the end token is past the end of the input text we have to use the position directly before
            //the end token.
            LexingMatch match = previous.getMatch();
            startPosition = match.getStartPosition() - 1;
        } else {
            LexingMatch match = previous.getMatch();
            startPosition = match.getStartPosition();
        }
        return new ParsingPosition(StringUtils.getTextPosition(originalInputString, startPosition), tokenStack);
    }

    @NotNull
    @Override
    public N prefixParse(@Nullable N previous, @NotNull Token<N> token) {
        PrefixParseAction<N> prefixParseAction = token.getPrefixParser();
        if (prefixParseAction == null) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Current token does not support prefix parsing", getParsingPosition()));
        }
        return prefixParseAction.parse(previous, this);
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