package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DirectEvaluationTest {

    @Test
    public void directEvaluationCalculator() {
        LanguageBuilder<Integer> builder = new LanguageBuilder<>();
        TokenDefinition<Integer> number = builder.newToken()
                .named("number")
                .matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText()))
                .build();
        TokenDefinition<Integer> addition = builder.newToken()
                .named("addition")
                .matchesString("+")
                .led((previous, parser, lexeme) -> previous + parser.subExpression(previous))
                .build();
        Language<Integer> lang = builder
                .addToken(number)
                .addToken(addition)
                .completeLanguage();
        String expr = "1+3";
        List<Lexeme<Integer>> lexemes = lang.newLexer().lex(expr);
        assertEquals("[number(1), addition(+), number(3), END]", lexemes.toString());
        assertEquals(4, (int) lang.newLexParser().tryParse(0, expr).getRootNode());

    }
}
