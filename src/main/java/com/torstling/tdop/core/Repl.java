package com.torstling.tdop.core;

import com.torstling.tdop.fluid.GenericParser;
import com.torstling.tdop.fluid.Language;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Repl<N> {

    private GenericParser<N> parser;
    @NotNull
    private final BufferedReader in;
    @NotNull
    private final BufferedWriter out;
    @NotNull
    private String languageName;

    public Repl(@NotNull Language<N> language) {
        this(language, new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    public Repl(@NotNull Language<N> language, @NotNull BufferedReader in, @NotNull BufferedWriter out) {
        languageName = language.getName();
        this.parser = language.getParser();
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
        out.write("welcome to the REPL for '" + languageName + "'");
        out.newLine();
        String line;
        while (true) {
            out.write(">");
            out.flush();
            line = in.readLine();
            if (line == null || line.matches("^(quit|end|q)$")) {
                out.write("leaving");
                out.flush();
                break;
            }
            ParseResult<N> result = parser.tryParse(line);
            if (result.isFailure()) {
                out.write("Error:" + result.getErrorMessage());
                out.newLine();
                out.flush();
            } else {
                out.write(result.getRootNode().toString());
                out.newLine();
                out.flush();
            }
        }
    }
}
