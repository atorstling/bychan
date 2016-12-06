package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * Created by alext on 2016-12-02.
 */
public class ReplBuilder<N> {

    @NotNull
    private final Language<N> language;
    @NotNull
    private Repl.RunFunction<N> runFunction;
    @NotNull
    private Repl.EvaluationFunction<N> evaluationFunction;
    @NotNull
    private ParseFunction<N> parseFunction;
    private BufferedReader in;
    private BufferedWriter out;

    public ReplBuilder(@NotNull final Language<N> language, ParseFunction<N> pf) {
        this.language = language;
        withIn(System.in).withOut(System.out).withParseFunction(pf);
        runFunction = (lexParser, parseFunction, snippet) -> {
            final ParseResult<N> result = lexParser.tryParse(snippet, parseFunction);
            if (result.isFailure()) {
                return ReplRunResult.error(result.getErrorMessage().toString());
            } else {
                return ReplRunResult.success(result.root());
            }
        };
        evaluationFunction = Repl::reflectionInvokeEvaluate;
    }

    @NotNull
    private ReplBuilder<N> withParseFunction(@NotNull final ParseFunction<N> parseFunction) {
        this.parseFunction = parseFunction;
        return this;
    }

    private ReplBuilder<N> withIn(InputStream in) {
        return withIn(new BufferedReader(new InputStreamReader(in)));
    }

    public ReplBuilder<N> withIn(@NotNull BufferedReader in) {
        this.in = in;
        return this;
    }

    public ReplBuilder<N> withOut(@NotNull BufferedWriter out) {
        this.out = out;
        return this;
    }

    public ReplBuilder<N> withRunFunction(@NotNull Repl.RunFunction<N> runFunction) {
        this.runFunction = runFunction;
        return this;
    }

    public ReplBuilder<N> withEvaluationFunction(@NotNull Repl.EvaluationFunction<N> evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
        return this;
    }

    public Repl<N> build() {
        return new Repl<>(language, in, out, runFunction, evaluationFunction, parseFunction);
    }

    public ReplBuilder<N> withOut(OutputStream out) {
        return withOut(new BufferedWriter(new OutputStreamWriter(out)));
    }
}
