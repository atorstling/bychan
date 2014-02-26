package com.torstling.tdop.fluid.json;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alext on 2/25/14.
 */
public class NonTerminalBuilder {
    private final String name;
    private final List<String> productions;

    public NonTerminalBuilder(String name) {
        this.name = name;
        this.productions = new ArrayList<>();
    }

    @NotNull
    public NonTerminalBuilder as(@NotNull final String production) {
        productions.add(production);
        return this;
    }

    public NonTerminalBuilder or() {
        return this;
    }

    @NotNull
    public NonTerminal build() {
        return new NonTerminal(name, productions);
    }
}
