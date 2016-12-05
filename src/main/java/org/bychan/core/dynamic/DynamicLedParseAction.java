package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.TokenParserCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link org.bychan.core.basic.LedParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 */
public interface DynamicLedParseAction<N> {
    /**
     * Parse this lexeme as the continuation of a block.
     *
     * @param left   The result of parsing the lexemes directly preceding this lexeme.
     * @param parser Useful for continuing the parsing
     * @param lexeme The lexeme which triggered this action
     * @return the resulting AST node.
     */
    N parse(@Nullable N left, @NotNull TokenParserCallback<N> parser, @NotNull Lexeme<N> lexeme);
}
