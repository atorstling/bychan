package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used to look up tokens in the list of {@link org.bychan.core.dynamic.TokenDefinition}s.
 *
 */
public class DelegatingTokenFinder<N> implements DynamicTokenFinder<N> {
    @Nullable
    private DynamicTokenFinder<N> delegate;

    @NotNull
    @Override
    public DynamicToken<N> getToken(@NotNull TokenKey soughtKey) {
        assert delegate != null;
        return delegate.getToken(soughtKey);
    }

    public void setDelegate(@SuppressWarnings("NullableProblems") @NotNull final DynamicTokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
