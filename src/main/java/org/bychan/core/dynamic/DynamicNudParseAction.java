package org.bychan.core.dynamic;

import org.bychan.core.basic.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link org.bychan.core.basic.NudParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 */
public interface DynamicNudParseAction<N> {
    /**
     * @param match               The match corresponding to the current lexeme
     * @param currentBindingPower The left binding power of the current token
     */
    @NotNull
    N parse(@Nullable N previous, @NotNull LexingMatch match, @NotNull UserParserCallback<N> parser, int currentBindingPower);
}
