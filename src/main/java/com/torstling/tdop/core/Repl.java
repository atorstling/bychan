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

    public Repl(@NotNull GenericParser<N> parser) {
        this(parser, new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    public Repl(@NotNull GenericParser<N> parser, @NotNull BufferedReader in, @NotNull BufferedWriter out) {
        this.parser = parser;
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
        out.write("Repl running");
        out.newLine();
        out.flush();
        String line;
        while ((line = in.readLine()) != null) {
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
