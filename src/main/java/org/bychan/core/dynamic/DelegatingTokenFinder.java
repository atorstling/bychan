package org.bychan.core.dynamic;

import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used to look up tokens in the list of {@link org.bychan.core.dynamic.TokenDefinition}s.
 *
 */
public class DelegatingTokenFinder<N> implements TokenFinder<N> {
    @Nullable
    private TokenFinder<N> delegate;

    @NotNull
    @Override
    public Token<N> getToken(@NotNull TokenKey soughtKey) {
        assert delegate != null;
        return delegate.getToken(soughtKey);
    }

    public void setDelegate(@SuppressWarnings("NullableProblems") @NotNull final TokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
