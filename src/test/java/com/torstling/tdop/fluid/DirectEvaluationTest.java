package com.torstling.tdop.fluid;

import com.torstling.tdop.core.Token;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DirectEvaluationTest {

    @Test
    public void directEvaluationCalculator() {
        LanguageBuilder<Integer> builder = new LanguageBuilder<>();
        TokenDefinition<Integer> number = builder.newToken()
                .named("number")
                .matchesPattern("[0-9]+")
                .supportsStandalone((symbolTable, match) -> Integer.parseInt(match.getText()))
                .build();
        TokenDefinition<Integer> addition = builder.newToken()
                .named("addition")
                .matchesString("+")
                .supportsInfix((match, previous, parser) -> previous + parser.expression(previous))
                .build();
        Language<Integer> lang = builder
                .newLowerPriorityLevel()
                .addToken(number)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(addition)
                .endLevel()
                .completeLanguage();
        String expr = "1+3";
        List<Token<Integer>> tokens = lang.getLexer().lex(expr);
        assertEquals("[number(1), addition(+), number(3), END]", tokens.toString());
        assertEquals(4, (int) lang.getParser().tryParse(0, expr).getRootNode());

    }
}