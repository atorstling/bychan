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

    @NotNull
    public ParseResult<N> tryParse(@Nullable N previous, @NotNull final String text) {
        LexingResult<N> lexingResult = lexer.tryLex(text);
        if (lexingResult.isFailure()) {
            LexingFailedInformation failureInfo = lexingResult.getFailureValue();
            return ParseResult.failure(new ParsingFailedInformation("Lexing failed:" + failureInfo.getMessage(), new LexingMatch(failureInfo.getLexingPosition().getStreamPosition(), failureInfo.getLexingPosition().getStreamPosition(), failureInfo.getLexingPosition().getMatchSection())));
        }
        return tryParse(previous, lexingResult.getSuccessValue());
    }

    @NotNull
    public ParseResult<N> tryParse(@Nullable N previous, @NotNull final List<Token<N>> tokens) {
        PrattParser<N> parser = new PrattParser<>(tokens);
        return parser.tryParse(previous, new ExpressionParserStrategy<>(0));
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull final List<Token<N>> tokens) {
        return tryParse(null, tokens);
    }

    public ParseResult<N> tryParse(@NotNull final String text) {
        return tryParse(null, text);
    }

    public N parse(@NotNull final String text) {
        return tryParse(text).getRootNode();
    }
}
