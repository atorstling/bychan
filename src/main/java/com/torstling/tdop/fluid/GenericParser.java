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
        List<Token<N>> tokens = lexer.lex(text);
        return tryParse(previous, tokens);
    }

    @NotNull
    public ParseResult<N> tryParse(@Nullable N previous, @NotNull final List<Token<N>> tokens) {
        PrattParser<N> parser = new PrattParser<>(tokens);
        return parser.tryParse(previous, new ExpressionParserStrategy<>(0));
    }
}
