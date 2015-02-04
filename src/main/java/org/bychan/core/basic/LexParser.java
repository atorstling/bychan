package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/**
 * Facade for a {@link org.bychan.core.basic.PrattParser} and a {@link org.bychan.core.basic.Lexer} which lexes and parses
 * an input text completely, making sure all input text has been properly processed before returning a result.
 *
 */
public class LexParser<N> {
    @NotNull
    private final Lexer<N> lexer;

    public LexParser(@NotNull final Lexer<N> lexer) {
        this.lexer = lexer;
    }

    public N parse(@NotNull final String text) {
        List<Lexeme<N>> lexemes = lexer.lex(text);
        PrattParser<N> parser = new PrattParser<>(lexemes, text);
        return parser.parseExpression(null, 0);
    }

    public ParseResult<N> tryParse(@NotNull final String text) {
        return tryParseInternal(null, text);
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull N left, @NotNull final String text) {
        return tryParseInternal(left, text);
    }

    @NotNull
    private ParseResult<N> tryParseInternal(@Nullable N left, @NotNull final String text) {
        LexingResult<N> lexingResult = lexer.tryLex(text);
        if (lexingResult.isFailure()) {
            ParsingFailedInformation parsingFailedInformation = ParsingFailedInformation.forFailedLexing(lexingResult.getFailureValue());
            return ParseResult.failure(parsingFailedInformation);
        }
        return tryParse(left, lexingResult.getSuccessValue(), text);
    }

    @NotNull
    private ParseResult<N> tryParse(@Nullable N left, @NotNull final List<Lexeme<N>> lexemes, @NotNull final String text) {
        PrattParser<N> parser = new PrattParser<>(lexemes, text);
        ParseResult<N> parsed = tryParse(() -> parser.parseExpression(left, 0));
        if (parsed.isSuccess()) {
            if (!parser.peek().getToken().equals(EndToken.get())) {
                return ParseResult.failure(ParsingFailedInformation.forFailedAfterLexing("The input stream was not completely parsed", parser.getParsingPosition()));
            }
            parser.swallow(EndToken.get());
        }
        return parsed;
    }

    @NotNull
    private ParseResult<N> tryParse(@NotNull Supplier<N> parseFunction) {
        try {
            N rootNode = parseFunction.get();
            return ParseResult.success(rootNode);
        } catch (ParsingFailedException e) {
            return ParseResult.failure(e.getParsingFailedInformation());
        }
    }
}
