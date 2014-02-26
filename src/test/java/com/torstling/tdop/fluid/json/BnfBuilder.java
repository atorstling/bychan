package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.GenericParser;
import com.torstling.tdop.fluid.LanguageBuilder;
import com.torstling.tdop.fluid.LevelLanguageBuilder;
import com.torstling.tdop.fluid.TokenDefinition;
import com.torstling.tdop.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BnfBuilder {
    @NotNull
    private final List<NonTerminalBuilder> nonTerminalBuilders;

    public BnfBuilder() {
        nonTerminalBuilders = new ArrayList<>();
    }

    public NonTerminalBuilder define(String nonTerminalName) {
        NonTerminalBuilder builder = new NonTerminalBuilder(nonTerminalName);
        nonTerminalBuilders.add(builder);
        return builder;
    }

    public GenericParser<Object> parser() {
        LanguageBuilder<Object> lb = new LanguageBuilder<>();
        Collection<NonTerminal> nonTerminals = Lists.transform(nonTerminalBuilders, NonTerminalBuilder::build);
        LevelLanguageBuilder<Object> levelBuilder = lb.newLowerPriorityLevel();
        for (NonTerminal nonTerminal : nonTerminals) {
            List<Production> productions = nonTerminal.getProductions();
            for (Production production : productions) {
                TokenDefinition<Object> token = lb
                        .newToken()
                        .named(production.getLexerPattern())
                        .matchesPattern(production.getLexerPattern())
                        .supportsStandalone(production.getAstBuilder()).build();
                levelBuilder.addToken(token);

            }
        }
        return levelBuilder.endLevel().completeLanguage().getParser();
    }
}
