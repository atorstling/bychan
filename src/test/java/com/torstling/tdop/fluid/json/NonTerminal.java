package com.torstling.tdop.fluid.json;

import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

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

    public void addTo(@NotNull final BnfGrammarBuilder g) {
        g.add(this);
    }

    @NotNull
    public String getName() {
        return name;
    }

    public String getCombinedProductionsPattern() {
        List<String> lexerPatterns = productions.stream().map(Production::getLexerPattern).collect(Collectors.toList());
        return lexerPatterns.stream().collect(Collectors.joining("||"));
    }

    @Nullable
    public Production findProductionForMatch(@NotNull final LexingMatch match) {
        for (Production production : productions) {
            String pattern = production.getLexerPattern();
            String text = match.getText();
            if (text.matches(pattern)) {
                return production;
            }
        }
        return null;
    }

    @NotNull
    public Production getProductionForMatch(@NotNull LexingMatch match) {
        //noinspection ConstantConditions
        return findProductionForMatch(match);
    }
}
