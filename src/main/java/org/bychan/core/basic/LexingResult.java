package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LexingResult<N> {
    @Nullable
    private final List<Token<N>> tokens;
    @Nullable
    private final LexingFailedInformation failedInformation;

    private LexingResult(@Nullable List<Token<N>> tokens, @Nullable LexingFailedInformation failedInformation) {
        if (tokens == null && failedInformation == null) {
            throw new IllegalArgumentException("Either argument must be present");
        }
        if (tokens != null && failedInformation != null) {
            throw new IllegalArgumentException("Both arguments cannot be present");
        }
        this.tokens = tokens;
        this.failedInformation = failedInformation;
    }

    boolean isSuccess() {
        return tokens != null;
    }

    public boolean isFailure() {
        return failedInformation != null;
    }

    @NotNull
    public List<Token<N>> getSuccessValue() {
        if (isFailure()) {
            throw new IllegalStateException("Was failure");
        }
        assert tokens != null;
        return tokens;
    }

    @NotNull
    public LexingFailedInformation getFailureValue() {
        if (isSuccess()) {
            throw new IllegalStateException("Was success");
        }
        assert failedInformation != null;
        return failedInformation;
    }

    public static <N> LexingResult<N> failure(LexingFailedInformation failedInformation) {
        return new LexingResult<>(null, failedInformation);
    }

    public static <N> LexingResult<N> success(List<Token<N>> tokens) {
        return new LexingResult<>(tokens, null);
    }
}