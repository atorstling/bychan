package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;
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

    public Repl(@NotNull Language<N> language) {
        this(language, new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    public Repl(@NotNull Language<N> language, @NotNull BufferedReader in, @NotNull BufferedWriter out) {
        languageName = language.getName();
        this.lexParser = language.newLexParser();
        this.in = in;
        this.out = out;
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
        out.write("End with an empty line or Ctrl+D.");
        out.newLine();
        out.flush();
        String snippet;
        while (true) {
            snippet = readSnippet();
            if (snippet.isEmpty() || snippet.matches("^(quit|end|q)$")) {
                out.write("leaving");
                out.newLine();
                out.flush();
                break;
            }
            ParseResult<N> result = lexParser.tryParse(snippet);
            if (result.isFailure()) {
                out.write("Error:" + result.getErrorMessage());
                out.newLine();
                out.flush();
            } else {
                N rootNode = result.getRootNode();
                out.write(rootNode.toString());
                Object evaluated = invokeEvaluate(rootNode);
                if (evaluated != null) {
                    out.write("=");
                    out.write(evaluated.toString());
                }
                out.newLine();
                out.flush();
            }
        }
    }

    private Object invokeEvaluate(N rootNode) {
        Method evaluate = getEvaluate(rootNode);
        if (evaluate == null) {
            return null;
        }
        try {
            return evaluate.invoke(rootNode);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "Exception:\n" + sw.toString();
        }
    }

    @Nullable
    private Method getEvaluate(@NotNull final N rootNode) {
        try {
            return rootNode.getClass().getMethod("evaluate");
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @NotNull
    private String readSnippet() throws IOException {
        List<String> lines = new ArrayList<>();
        while (true) {
            out.write(">");
            out.flush();
            String line = in.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            lines.add(line);
        }
        return String.join("\n", lines);
    }
}
