package org.bychan.core.basic;

import org.bychan.core.utils.BoundedFifo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexemeStack<N> {
    private static final LexemeStack<Object> EMPTY = new LexemeStack<>();
    @NotNull
    private final ArrayDeque<Lexeme<N>> lexemes;
    @NotNull
    private final BoundedFifo<Lexeme<N>> history;

    @SafeVarargs
    @TestOnly
    public LexemeStack(Lexeme<N>... lexemes) {
        this(Arrays.asList(lexemes));
    }

    public LexemeStack(List<Lexeme<N>> lexemes) {
        this.lexemes = new ArrayDeque<>(lexemes);
        history = new BoundedFifo<>(2);
    }

    @NotNull
    public static <N> LexemeStack<N> empty() {
        //noinspection unchecked
        return (LexemeStack<N>) EMPTY;
    }

    public Lexeme<N> pop() {
        Lexeme<N> popped = lexemes.pop();
        history.putLast(popped);
        return popped;
    }

    public Lexeme<N> peek() {
        return lexemes.peek();
    }

    @Nullable
    public Lexeme<N> history(int i) {
        return history.findFromFront(i);
    }

    @NotNull
    public List<Lexeme<N>> remaining() {
        return new ArrayList<>(lexemes);
    }
}
