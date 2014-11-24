package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.List;

public class PrattParser<N> implements TokenParserCallback<N> {

    @NotNull
    private final ArrayDeque<Token<N>> tokens;

    public PrattParser(List<? extends Token<N>> tokens) {
        this.tokens = new ArrayDeque<>(tokens);
    }

    @Override
    @NotNull
    public ParseResult<N> tryParse(@Nullable N previous, @NotNull Expression<N> statement) {
        try {
            N rootNode = parse(previous, statement);
            return ParseResult.success(rootNode);
        } catch (ParsingFailedException e) {
            return ParseResult.failure(e.getParsingFailedInformation());
        }
    }

    private N parse(@Nullable N previous, @NotNull Expression<N> statement) {
        return statement.parse(previous, this);
    }

    @NotNull
    public Token<N> swallow(@NotNull TokenType<N> type) {
        Token<N> next = tokens.pop();
        if (!next.getType().equals(type)) {
            throw new ParsingFailedException(new ParsingFailedInformation("Expected a token of type '" + type + "', but got '" + next + "'", next.getMatch().toParsingPosition()));
        }
        return next;
    }

    @NotNull
    @Override
    public Token<N> peek() {
        return tokens.peek();
    }

    @NotNull
    @Override
    public Token<N> pop() {
        return tokens.pop();
    }
}
