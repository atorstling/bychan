package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2015-01-05.
 */
interface TokenDefinitionOwner<N> {
    int increaseUnnamedTokenCounter();
    void tokenBuilt(TokenDefinitionBuilder<N> builder, @NotNull TokenDefinition<N> token);
}
