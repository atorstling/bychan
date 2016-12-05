package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.Parser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link org.bychan.core.basic.NudParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 */
public interface DynamicNudParseAction<N> {
    /**
     * Parse this lexeme as the start of a new block.
     *
     * @param left   the result of parsing the lexemes directly preceding this lexeme. This parameter is passed
     *               in the NUD case even though it is not specified in the original paper. The reason is that
     *               it might be handy to know the directly preceding result when implementing nested scopes and
     *               other constructions. Without this knowledge the parser must keep external extra state for these
     *               things.
     * @param parser Useful for continuing the parsing
     * @param lexeme The current lexeme
     * @return the resulting AST node.
     */
    @NotNull
    N parse(@Nullable N left, @NotNull Parser<N> parser, @NotNull Lexeme<N> lexeme);
}
