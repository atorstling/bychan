package com.torstling.tdop.fluid.json;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by alext on 2/26/14.
 */
public class NonTerminal {
    @NotNull
    private final String name;
    @NotNull
    private final List<Production> productions;

    public NonTerminal(@NotNull String name, @NotNull List<Production> productions) {
        this.name = name;
        this.productions = productions;
    }

    @NotNull
    public List<Production> getProductions() {
        return productions;
    }
}
