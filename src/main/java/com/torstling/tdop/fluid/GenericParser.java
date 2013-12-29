package com.torstling.tdop.fluid;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GenericParser<N extends AstNode> {
    @NotNull
    private final Lexer<N> lexer;

    public GenericParser(@NotNull final Lexer<N> lexer) {
        this.lexer = lexer;
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull N parent, @NotNull final String text) {
        List<Token<N>> tokens = lexer.lex(text);
        return tryParse(parent, tokens);
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull N parent, @NotNull final List<Token<N>> tokens) {
        PrattParser<N> parser = new PrattParser<>(tokens);
        return parser.tryParse(parent);
    }
}
