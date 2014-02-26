package com.torstling.tdop.fluid.json;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by alext on 2/26/14.
 */
public class NonTerminal {
    private final String name;
    private final List<String> productions;

    public NonTerminal(String name, List<String> productions) {
        this.name = name;
        this.productions = productions;
    }

    @NotNull
    public List<String> getProductions() {
        return productions;
    }
}
