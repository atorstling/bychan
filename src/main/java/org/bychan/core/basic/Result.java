package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alext on 2016-12-06.
 */
public class Result<S, F> {
    @Nullable
    private final S successValue;
    @Nullable
    private final F failureValue;

    public Result(@Nullable S successValue, @Nullable F failureValue) {
        if (successValue == null && failureValue == null) {
            throw new IllegalStateException("Must specify either success or failure value");
        }
        if (successValue != null && failureValue != null) {
            throw new IllegalStateException("Cannot specify both success and failure value");
        }
        this.successValue = successValue;
        this.failureValue = failureValue;
    }

    @NotNull
    public static <S, F> Result<S, F> success(@NotNull final S successValue) {
        return new Result<>(successValue, null);
    }

    @NotNull
    public static <S, F> Result<S, F> failure(@NotNull final F failureValue) {
        return new Result<>(null, failureValue);
    }

    public boolean isFailure() {
        return failureValue != null;
    }

    public F getFailureValue() {
        checkFailure();
        return failureValue;
    }

    private void checkFailure() {
        if (!isFailure()) {
            throw new IllegalStateException("Was successful");
        }
    }

    public S getSuccessValue() {
        checkSuccess();
        return successValue;
    }

    private void checkSuccess() {
        if (!isSuccess()) {
            throw new IllegalStateException("Was failure");
        }
    }

    private boolean isSuccess() {
        return successValue != null;
    }
}
