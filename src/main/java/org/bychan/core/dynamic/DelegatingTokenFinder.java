package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used to look up token types in the list of {@link org.bychan.core.dynamic.TokenDefinition}s.
 * @param <N>
 */
public class DelegatingTokenFinder<N> implements TokenFinder<N> {
    @Nullable
    private TokenFinder<N> delegate;

    @NotNull
    @Override
    public DynamicTokenType<N> getTokenTypeFor(@NotNull TokenDefinition<N> tokenDefinition) {
        assert delegate != null;
        return delegate.getTokenTypeFor(tokenDefinition);
    }

    public void setDelegate(@SuppressWarnings("NullableProblems") @NotNull final TokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
