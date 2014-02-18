package com.torstling.tdop.fluid;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GenericParser<N, S> {
    @NotNull
    private final Lexer<N,S> lexer;

    public GenericParser(@NotNull final Lexer<N,S> lexer) {
        this.lexer = lexer;
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull N previous, @NotNull final String text) {
        List<Token<N,S>> tokens = lexer.lex(text);
        return tryParse(previous, tokens);
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull N previous, @NotNull final List<Token<N, S>> tokens) {
        PrattParser<N,S> parser = new PrattParser<>(tokens);
        return parser.tryParse(new ExpressionParserStrategy<>(previous, 0));
    }
}
