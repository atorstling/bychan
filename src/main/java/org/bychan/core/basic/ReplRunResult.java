package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2016-12-06.
 */
class ReplRunResult<N> {
    Result<N, String> delegate;

    public ReplRunResult(@NotNull final Result<N, String> delegate) {
        this.delegate = delegate;
    }

    public static <N> ReplRunResult<N> error(String error) {
        return new ReplRunResult<>(Result.<N, String>failure(error));
    }

    public static <N> ReplRunResult<N> success(N result) {
        return new ReplRunResult<>(Result.<N, String>success(result));
    }

    public boolean isFailure() {
        return delegate.isFailure();
    }

    public String getErrorMessage() {
        return delegate.getFailureValue();
    }

    public N getRootNode() {
        return delegate.getSuccessValue();
    }
}
