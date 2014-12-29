package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used to look up token types in the list of {@link org.bychan.core.dynamic.TokenDefinition}s.
 *
 */
public class DelegatingTokenFinder<N> implements DynamicTokenFinder<N> {
    @Nullable
    private DynamicTokenFinder<N> delegate;

    @NotNull
    @Override
    public DynamicTokenType<N> getTokenTypeFor(@NotNull TokenKey soughtKey) {
        assert delegate != null;
        return delegate.getTokenTypeFor(soughtKey);
    }

    public void setDelegate(@SuppressWarnings("NullableProblems") @NotNull final DynamicTokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
