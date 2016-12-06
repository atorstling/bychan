package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

/**
 * Facade for a {@link org.bychan.core.basic.PrattParser} and a {@link org.bychan.core.basic.Lexer} which lexes and parses
 * an input text completely, making sure all input text has been properly processed before returning a result.
 */
public class LexParser<N> {
    @NotNull
    private final Lexer<N> lexer;

    public LexParser(@NotNull final Lexer<N> lexer) {
        this.lexer = lexer;
    }

    @NotNull
    public ParseResult<N> tryParse(@NotNull final String text, ParseFunction<N> f) {
        LexingResult<N> lexingResult = lexer.tryLex(text);
        if (lexingResult.isFailure()) {
            LexingFailedInformation lexParsingFailedInformation = lexingResult.getFailureValue();
            return ParseResult.failure(lexParsingFailedInformation);
        }
        final PrattParser<N> p1 = new PrattParser<>(lexingResult.getSuccessValue(), text);
        final ParseResult<N> parsed = tryParse(p1, f);
        if (parsed.isSuccess()) {
            if (!p1.peek().isA("end")) {
                return ParseResult.failure(new ParsingFailedInformation("The input stream was not completely parsed", p1.position()));
            }
            p1.swallow("end");
        }
        return parsed;
    }

    @NotNull
    private ParseResult<N> tryParse(PrattParser<N> p, ParseFunction<N> f) {
        try {
            N rootNode = f.parse(p);
            return ParseResult.success(rootNode);
        } catch (ParsingFailedException e) {
            return ParseResult.failure(e.getFailureInformation());
        }
    }


}
