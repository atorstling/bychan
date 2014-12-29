package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

/**
 * Traces the current parsing position in the original input data. Was useful to factor out
 * in order to simplify the testing of the parser. With a mock {@link org.bychan.core.basic.PositionTracer} you
 * can test the parser with a list of tokens only and can skip the original input data.
 *
 */
interface PositionTracer<N> {
    @NotNull
    ParsingPosition getParsingPosition(@NotNull TokenStack<N> tokenStack);
}
