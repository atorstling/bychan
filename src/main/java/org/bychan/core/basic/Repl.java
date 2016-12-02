package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;
import org.bychan.core.utils.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Repl<N> implements Runnable {

    private final LexParser<N> lexParser;
    @NotNull
    private final BufferedReader in;
    @NotNull
    private final BufferedWriter out;
    @NotNull
    private final String languageName;
    private final EvaluationFunction evaluationFunction;

    interface ParsingFunction<N> {
        /**
         * A callback used to parse a snippet.
         * Parses the given snippet with the parser. You may add try..catch to swallow exceptions if you wish .
         * @param lexParser the lexParser to use to parse the ...
         * @param snippet ... snippet.
         * @return A parse result.
         */
        ParseResult<N> parse(LexParser<N> lexParser, String snippet);
    }

    interface EvaluationFunction<N> {
        /**
         * A callback used to evaluate a node.
         * To your help you may use {@link #reflectionInvokeEvaluate(Object)} which
         * can call "evaluate" on the node. You may surround the call with try..catch or modify the call as you
         * wish
         * @param node the node
         * @return the evaluated value, or <code>null</code> if you couldn't find any evaluation method. In
         * this case no printout of the evaluation will be done.
         */
        Object evaluate(N node);
    }

    private final ParsingFunction<N> parsingFunction;

    public Repl(@NotNull Language<N> language, @NotNull BufferedReader in, @NotNull BufferedWriter out, ParsingFunction<N> parsingFunction, EvaluationFunction evaluationFunction) {
        languageName = language.getName();
        this.lexParser = language.newLexParser();
        this.in = in;
        this.out = out;
        this.parsingFunction = parsingFunction;
        this.evaluationFunction = evaluationFunction;
    }


    public void run() {
        try {
            runInternal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runInternal() throws IOException {
        out.write("Welcome to the REPL for '" + languageName + "'.");
        out.newLine();
        out.write("End a statement by adding an empty line. Quit by entering 'q', 'quit' or 'end' or pressing Ctrl+D.");
        out.newLine();
        out.flush();
        String snippet;
        while (true) {
            snippet = readSnippet();
            if (snippet == null || snippet.matches("^(quit|end|q)$")) {
                out.write("leaving");
                out.newLine();
                out.flush();
                break;
            }
            ParseResult<N> result = parsingFunction.parse(lexParser, snippet);
            if (result.isFailure()) {
                out.write("Error:" + result.getErrorMessage());
                out.newLine();
                out.flush();
            } else {
                N rootNode = result.getRootNode();
                out.write(rootNode.toString());

                Object evaluated = evaluationFunction.evaluate(rootNode);
                if (evaluated != null) {
                    out.write("=");
                    out.write(evaluated.toString());
                }
                out.newLine();
                out.flush();
            }
        }
    }

    @Nullable
    public static <N> Object reflectionInvokeEvaluate(N node) {
        Method evaluateMethod = getEvaluateMethod(node);
        if (evaluateMethod == null) {
            return null;
        }
        try {
            return evaluateMethod.invoke(node);
        } catch (IllegalAccessException e) {
            // Could not access function, treat as if it doesn't exist
            return null;
        } catch (InvocationTargetException e) {
            ExceptionUtils.sneakyThrow(e.getCause());
            throw new IllegalStateException("Failed to throw InvocationTargetException", e);
        }
    }

    @Nullable
    private static <N> Method getEvaluateMethod(@NotNull final N node) {
        try {
            return node.getClass().getMethod("evaluate");
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Nullable
    private String readSnippet() throws IOException {
        List<String> lines = new ArrayList<>();
        while (true) {
            out.write(">");
            out.flush();
            String line = in.readLine();
            if (line == null) {
                //EOF. Return what we've got, but if we haven't got anything we return null to signal abort
                if (lines.isEmpty()) {
                    return null;
                }
                break;
            }
            if (line.isEmpty()) {
                break;
            }
            lines.add(line);
        }
        return String.join("\n", lines);
    }
}
