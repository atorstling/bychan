package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.StandaloneAstBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alext on 2/25/14.
 */
public class NonTerminalBuilder {
    private final String name;
    private final List<Production> productions;

    public NonTerminalBuilder(String name) {
        this.name = name;
        this.productions = new ArrayList<>();
    }

    @NotNull
    public NonTerminalBuilder as(@NotNull final String lexerPattern, @NotNull final StandaloneAstBuilder<Object> astBuilder) {
        productions.add(new Production(lexerPattern, astBuilder));
        return this;
    }

    public NonTerminalBuilder or() {
        return this;
    }

    @NotNull
    public NonTerminal build() {
        return new NonTerminal(name, productions);
    }

    public void addTo(@NotNull final BnfGrammarBuilder grammarBuilder) {
        build().addTo(grammarBuilder);
    }

    public NonTerminalBuilder as(@NotNull NonTerminal nonTerminal) {
        return this;
    }
}
