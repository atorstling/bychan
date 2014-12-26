package org.bychan.fluid;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/**
 * Facade for a {@link org.bychan.core.PrattParser} and a {@link org.bychan.core.Lexer} which lexes and parses
 * an input text completely, making sure all input text has been properly processed before returning a result.
 * @param <N>
 */
public class LexParser<N> {
    @NotNull
    private final Lexer<N> lexer;

    public LexParser(@NotNull final Lexer<N> lexer) {
        this.lexer = lexer;
    }

    public N parse(@NotNull final String text) {
        return tryParse(text).getRootNode();
    }

    public ParseResult<N> tryParse(@NotNull final String text) {
        return tryParseInternal(null, text);
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull N previous, @NotNull final String text) {
        return tryParseInternal(previous, text);
    }

    @NotNull
    private ParseResult<N> tryParseInternal(@Nullable N previous, @NotNull final String text) {
        LexingResult<N> lexingResult = lexer.tryLex(text);
        if (lexingResult.isFailure()) {
            ParsingFailedInformation parsingFailedInformation = ParsingFailedInformation.forFailedLexing(lexingResult.getFailureValue());
            return ParseResult.failure(parsingFailedInformation);
        }
        return tryParse(previous, lexingResult.getSuccessValue());
    }

    @NotNull
    private ParseResult<N> tryParse(@Nullable N previous, @NotNull final List<Token<N>> tokens) {
        PrattParser<N> parser = new PrattParser<>(tokens);
        ParseResult<N> parsed = tryParse(() -> parser.parseExpression(previous, 0));
        if (parsed.isSuccess()) {
            if (!parser.peek().getType().equals(EndTokenType.get())) {
                return ParseResult.failure(ParsingFailedInformation.forFailedAfterLexing("The input stream was not completely parsed", parser.getParsingPosition()));
            }
            parser.swallow(EndTokenType.get());
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
