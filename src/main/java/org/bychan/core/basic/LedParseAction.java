package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A "Left Denotation" parse action. Typically used by infix and suffix parse actions.
 */
public interface LedParseAction<N> {
    /**
     * Parse this lexeme as the continuation of a block.
     *
     * @param previous The result of parsing the lexemes directly preceding this lexeme.
     * @param parser   Useful for continuing the parsing
     * @return the resulting AST node.
     */
    @NotNull
    N parse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);
}
