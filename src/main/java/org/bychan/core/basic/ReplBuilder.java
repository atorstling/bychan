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
    private Repl.ParsingFunction<N> parsingFunction;
    @NotNull
    private Repl.EvaluationFunction<N> evaluationFunction;
    private BufferedReader in;
    private BufferedWriter out;


    public ReplBuilder(@NotNull final Language<N> language) {
        this.language = language;
        withIn(System.in).withOut(System.out);
        parsingFunction = (lexParser, snippet) -> {
            final ParseResult<N> result = lexParser.tryParse(snippet);
            if (result.isFailure()) {
                return ReplRunResult.error(result.getErrorMessage().toString());
            } else {
                return ReplRunResult.success(result.getRootNode());
            }
        };
        evaluationFunction = Repl::reflectionInvokeEvaluate;
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

    public ReplBuilder<N> withParsingFunction(@NotNull Repl.ParsingFunction<N> parsingFunction) {
        this.parsingFunction = parsingFunction;
        return this;
    }

    public ReplBuilder<N> withEvaluationFunction(@NotNull Repl.EvaluationFunction<N> evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
        return this;
    }

    public Repl<N> build() {
        return new Repl<>(language, in, out, parsingFunction, evaluationFunction);
    }

    public ReplBuilder<N> withOut(OutputStream out) {
        return withOut(new BufferedWriter(new OutputStreamWriter(out)));
    }
}
