package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrattParser<N> implements TokenParserCallback<N> {
    @NotNull
    private final LexemeStack<N> lexemeStack;
    @NotNull
    private final PositionTracer<N> positionTracer;

    PrattParser(@NotNull List<Lexeme<N>> lexemes, @NotNull final PositionTracer<N> positionTracer) {
        this.lexemeStack = new LexemeStack<>(lexemes);
        this.positionTracer = positionTracer;
    }

    public PrattParser(@NotNull List<Lexeme<N>> lexemes, @NotNull final String originalInputString) {
        this(lexemes, new PositionTracerImpl<>(originalInputString));
    }

    /**
     * Parse upcoming tokens from the stream into an expression, and keep going
     * until token binding powers drop down to or below the supplied right binding power. If this
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
    public N parseExpression(@Nullable N previous, final int rightBindingPower) {
        // An expression always starts with a symbol which can qualify as a nud value
        // i.e
        // "+" as in "positive", used in for instance "+3 + 5", parses to +(rest of expression)
        // "-" as in "negative", used in for instance "-3 + 5", parses to -(rest of expression)
        // "(" as in "start sub-expression", used in for instance "(3)", parses rest of expression with 0 strength,
        //         which keeps going until next 0-valued token is encountered (")" or end)
        // any digit, used in for instance "3", parses to 3.
        Lexeme<N> firstLexeme = pop();
        final N first = nud(previous, firstLexeme);
        // When we have the nud parsing settled, we cannot be sure that the parsing is done. Digit parsing
        // returns almost immediately for instance. If the nud parse swallowed all the expression, only the end
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
        // The addition operators led-parser is then called by the top-level expression parser,
        // passing (1*2) into it as the expression parsed so far. It will then proceed to swallow the 3,
        // completing the expression.
        return parseLoop(first, rightBindingPower);
    }

    private N parseLoop(@NotNull final N currentLeftHandSide, final int rightBindingPower) {
        Lexeme<N> peekedLexeme = peek();
        if (peekedLexeme.leftBindingPower() > rightBindingPower) {
            //Parsing happens by passing the previous LHS to the operator, which will continue parsing.
            Lexeme<N> takenLexeme = pop();
            N nextExpression = led(currentLeftHandSide, takenLexeme);
            return parseLoop(nextExpression, rightBindingPower);
        }
        return currentLeftHandSide;
    }

    private N led(@NotNull N currentLeftHandSide, @NotNull Lexeme<N> takenLexeme) {
        LedParseAction<N> ledParseAction = takenLexeme.getLed();
        if (ledParseAction == null) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Current token does not support led parsing", getParsingPosition()));
        }
        return ledParseAction.parse(currentLeftHandSide, this);
    }

    @NotNull
    public ParsingPosition getParsingPosition() {
        return positionTracer.getParsingPosition(lexemeStack);
    }

    @NotNull
    public Lexeme<N> swallow(@NotNull Token<N> token) {
        Lexeme<N> next = lexemeStack.pop();
        if (!next.getToken().equals(token)) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Expected token '" + token.getName() + "', but got '" + next + "'", getParsingPosition()));
        }
        return next;
    }

    @NotNull
    @Override
    public N nud(@Nullable N previous, @NotNull Lexeme<N> lexeme) {
        NudParseAction<N> nudParseAction = lexeme.getNud();
        if (nudParseAction == null) {
            throw new ParsingFailedException(ParsingFailedInformation.forFailedAfterLexing("Current lexeme does not support nud parsing", getParsingPosition()));
        }
        return nudParseAction.parse(previous, this);
    }

    @NotNull
    @Override
    public Lexeme<N> peek() {
        return lexemeStack.peek();
    }

    @NotNull
    private Lexeme<N> pop() {
        return lexemeStack.pop();
    }
}
