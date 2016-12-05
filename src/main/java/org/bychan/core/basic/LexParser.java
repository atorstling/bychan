package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * Facade for a {@link org.bychan.core.basic.PrattParser} and a {@link org.bychan.core.basic.Lexer} which lexes and parses
 * an input text completely, making sure all input text has been properly processed before returning a result.
 *
 */
public class LexParser<N> {
    @NotNull
    private final Lexer<N> lexer;

    public interface ParseFunction<N> {
        ParseResult<N> parse(PrattParser<N> p);
    }

    public LexParser(@NotNull final Lexer<N> lexer) {
        this.lexer = lexer;
    }


    public N parse(@NotNull final String text) {
        return getParser(text).parseExpression();
    }

    @NotNull
    public PrattParser<N> getParser(@NotNull String text) {
        List<Lexeme<N>> lexemes = lexer.lex(text);
        return new PrattParser<>(lexemes, text);
    }

    public ParseResult<N> tryParse(@NotNull final String text) {
        return tryParse(text, p -> p.tryParseFully(null, 0));
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull N left, @NotNull final String text) {
        return tryParse(text, p -> p.tryParseFully(left, 0));
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull final String text, ParseFunction<N> parseFunction) {
        LexingResult<N> lexingResult = lexer.tryLex(text);
        if (lexingResult.isFailure()) {
            LexingFailedInformation lexParsingFailedInformation = lexingResult.getFailureValue();
            return ParseResult.failure(lexParsingFailedInformation);
        }
        final PrattParser<N> p = new PrattParser<>(lexingResult.getSuccessValue(), text);
        return parseFunction.parse(p);
    }

    @NotNull
    public Lexer<N> getLexer() {
        return lexer;
    }
}
