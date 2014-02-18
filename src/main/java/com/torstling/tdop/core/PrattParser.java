package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

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
    public ParseResult<N> tryParse(@NotNull ParserStrategy<N> strategy) {
        try {
            N rootNode = parse(strategy);
            return ParseResult.success(rootNode);
        } catch (ParsingFailedException e) {
            return ParseResult.failure(e.getParsingFailedInformation());
        }
    }

    private N parse(@NotNull ParserStrategy<N> strategy) {
        return strategy.parse(tokens, this);
    }

    @NotNull
    public Token<N> swallow(@NotNull TokenType<N> type) {
        Token<N> next = tokens.pop();
        if (!next.getType().equals(type)) {
            throw new ParsingFailedException(new ParsingFailedInformation("Expected a token of type '" + type + "', but got '" + next + "'", next.getMatch()));
        }
        return next;
    }

    @NotNull
    @Override
    public Token<N> peek() {
        return tokens.peek();
    }
}
