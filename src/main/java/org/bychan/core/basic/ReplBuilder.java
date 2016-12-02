package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * Created by alext on 2016-12-02.
 */
public class ReplBuilder<T> {

    @NotNull
    private final Language<T> language;
    @NotNull
    private Repl.ParsingFunction<T> parsingFunction;
    @NotNull
    private Repl.EvaluationFunction evaluationFunction;
    @NotNull
    private BufferedReader in;
    @NotNull
    private BufferedWriter out;


    public ReplBuilder(@NotNull final Language<T> language) {
        this.language = language;
        withIn(System.in).withOut(System.out);
        parsingFunction = LexParser::tryParse;
        evaluationFunction = Repl.EvaluationReflectionRunner::run;
    }

    private ReplBuilder<T> withIn(InputStream in) {
        return withIn(new BufferedReader(new InputStreamReader(in)));
    }

    public ReplBuilder<T> withIn(@NotNull BufferedReader in) {
        this.in = in;
        return this;
    }

    public ReplBuilder<T> withOut(@NotNull BufferedWriter out) {
        this.out = out;
        return this;
    }

    public ReplBuilder<T> withParsingFunction(@NotNull Repl.ParsingFunction<T> parsingFunction) {
        this.parsingFunction = parsingFunction;
        return this;
    }

    public ReplBuilder<T> withEvaluationFunction(@NotNull Repl.EvaluationFunction evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
        return this;
    }

    public Repl<T> build() {
        return new Repl<T>(language, in, out, parsingFunction, evaluationFunction);
    }

    public ReplBuilder<T> withOut(OutputStream out) {
        return withOut(new BufferedWriter(new OutputStreamWriter(out)));
    }
}
