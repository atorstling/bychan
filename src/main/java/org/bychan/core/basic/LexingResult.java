package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LexingResult<N> {
    @Nullable
    private final List<Lexeme<N>> lexemes;
    @Nullable
    private final LexingFailedInformation failedInformation;

    private LexingResult(@Nullable List<Lexeme<N>> lexemes, @Nullable LexingFailedInformation failedInformation) {
        if (lexemes == null && failedInformation == null) {
            throw new IllegalArgumentException("Either argument must be present");
        }
        if (lexemes != null && failedInformation != null) {
            throw new IllegalArgumentException("Both arguments cannot be present");
        }
        this.lexemes = lexemes;
        this.failedInformation = failedInformation;
    }

    boolean isSuccess() {
        return lexemes != null;
    }

    public boolean isFailure() {
        return failedInformation != null;
    }

    @NotNull
    public List<Lexeme<N>> getSuccessValue() {
        if (isFailure()) {
            throw new IllegalStateException("Was failure");
        }
        assert lexemes != null;
        return lexemes;
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

    public static <N> LexingResult<N> success(List<Lexeme<N>> lexemes) {
        return new LexingResult<>(lexemes, null);
    }
}
