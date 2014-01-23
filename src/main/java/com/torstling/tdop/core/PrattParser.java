package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.List;

public class PrattParser<N extends AstNode, S> implements TokenParserCallback<N,S> {

    @NotNull
    private final ArrayDeque<Token<N,S>> tokens;

    public PrattParser(List<? extends Token<N,S>> tokens) {
        this.tokens = new ArrayDeque<>(tokens);
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull ParserStrategy<N, S> strategy) {
        try {
            N rootNode = parse(strategy);
            return ParseResult.success(rootNode);
        } catch (ParsingFailedException e) {
            return ParseResult.failure(e.getMessage());
        }
    }

    public N parse(@NotNull ParserStrategy<N, S> strategy) {
        return strategy.parse(tokens, this);
    }

    @NotNull
    @Override
    public N expression(S symbolTable, int powerFloor) {
        return parse(new ExpressionParserStrategy<N, S>(symbolTable, powerFloor));
    }

    @NotNull
    public Token<N,S> swallow(@NotNull TokenType<N,S> type) {
        Token<N,S> next = tokens.pop();
        if (!next.getType().equals(type)) {
            throw new ParsingFailedException("Expected a token of type '" + type + "', but got '" + next + "'", next.getMatch());
        }
        return next;
    }

    @NotNull
    @Override
    public Token<N,S> peek() {
        return tokens.peek();
    }
}
