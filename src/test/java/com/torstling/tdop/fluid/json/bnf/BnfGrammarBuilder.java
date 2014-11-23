package com.torstling.tdop.fluid.json.bnf;

import com.torstling.tdop.fluid.GenericParser;
import com.torstling.tdop.fluid.LanguageBuilder;
import com.torstling.tdop.fluid.LevelLanguageBuilder;
import com.torstling.tdop.fluid.TokenDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BnfGrammarBuilder {
    @NotNull
    private final List<NonTerminal> nonTerminals;

    public BnfGrammarBuilder() {
        this.nonTerminals = new ArrayList<>();
    }

    public GenericParser<Object> parser() {
        LanguageBuilder<Object> lb = new LanguageBuilder<>();
        List<NonTerminal> allNonTerminals = collectAllNonTerminals();
        LevelLanguageBuilder<Object> levelBuilder = lb.newLowerPriorityLevel();
        for (NonTerminal nonTerminal : allNonTerminals) {
            List<Production> terminalProductions = nonTerminal.getProductions();
            for (Production terminalProduction : terminalProductions) {
                TokenDefinition<Object> token = lb
                        .newToken()
                        .named(terminalProduction.getName())
                        .matchesPattern(terminalProduction.getLexerPattern())
                        .supportsStandalone(terminalProduction.getAstBuilder()).build();
                levelBuilder.addToken(token);

            }
        }
        return levelBuilder.endLevel().completeLanguage().getParser();
    }

    private List<NonTerminal> collectAllNonTerminals() {
        return nonTerminals;
    }

    public void add(@NotNull final NonTerminal nonTerminal) {
        nonTerminals.add(nonTerminal);
    }
}
