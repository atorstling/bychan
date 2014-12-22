package com.torstling.tdop.fluid;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            LexingFailedInformation failureInfo = lexingResult.getFailureValue();
            ParsingFailedInformation parsingFailedInformation = ParsingFailedInformation.forFailedLexing(lexingResult.getFailureValue());
            return ParseResult.failure(parsingFailedInformation);
        }
        return tryParse(previous, lexingResult.getSuccessValue());
    }

    @NotNull
    private ParseResult<N> tryParse(@Nullable N previous, @NotNull final List<Token<N>> tokens) {
        PrattParser<N> parser = new PrattParser<>(tokens);
        Expression<N> strategy = new Expression<>(0);
        ParseResult<N> parsed = parser.tryParse(previous, strategy);
        if (parsed.isSuccess()) {
            parser.swallow(EndTokenType.get());
        }
        return parsed;
    }
}
