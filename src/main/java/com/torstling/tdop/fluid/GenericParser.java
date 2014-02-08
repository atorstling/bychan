package com.torstling.tdop.fluid;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GenericParser<N extends AstNode, S> {
    @NotNull
    private final Lexer<N,S> lexer;

    public GenericParser(@NotNull final Lexer<N,S> lexer) {
        this.lexer = lexer;
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull S symbolTable, @NotNull final String text) {
        List<Token<N,S>> tokens = lexer.lex(text);
        return tryParse(symbolTable, tokens);
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull S symbolTable, @NotNull final List<Token<N,S>> tokens) {
        PrattParser<N,S> parser = new PrattParser<>(tokens);
        return parser.tryParse(new ExpressionParserStrategy<>(symbolTable, 0));
    }
}
