package org.bychan.fluid;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GenericParser<N> {
    @NotNull
    private final Lexer<N> lexer;

    public GenericParser(@NotNull final Lexer<N> lexer) {
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
        ParseResult<N> parsed = tryParseExpression(previous, 0, parser);
        if (parsed.isSuccess()) {
            parser.swallow(EndTokenType.get());
        }
        return parsed;
    }

    @NotNull
    public ParseResult<N> tryParseExpression(@Nullable N previous, final int powerFloor, @NotNull PrattParser<N> parser) {
        Supplier<N> parseFunction = () -> parser.parseExpression(previous, powerFloor);
        return tryParse(parseFunction);
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
