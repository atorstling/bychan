package com.torstling.tdop.fluid.json;

/**
 * Created by alext on 2/26/14.
 */
public class BnfBuilder {

    public NonTerminalBuilder define(String nonTerminalName) {
        return new NonTerminalBuilder(nonTerminalName);
    }

    public BnfGrammarBuilder newGrammar() {
        return new BnfGrammarBuilder();
    }
}
