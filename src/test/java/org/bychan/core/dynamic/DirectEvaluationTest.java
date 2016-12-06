package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DirectEvaluationTest {

    @Test
    public void directEvaluationCalculator() {
        LanguageBuilder<Integer> lb = new LanguageBuilder<>();
        lb.newToken().named("number").matchesPattern("[0-9]+")
                .nud((left, parser, lexeme) -> Integer.parseInt(lexeme.getText()))
                .build();
        lb.newToken().named("addition").matchesString("+")
                .led((left, parser, lexeme) -> left + parser.expression(left, lexeme.leftBindingPower()))
                .build();
        Language<Integer> lang = lb.completeLanguage();
        String expr = "1+3";
        List<Lexeme<Integer>> lexemes = lang.newLexer().lex(expr);
        assertEquals("[number(1), addition(+), number(3), END]", lexemes.toString());
        assertEquals(4, (int) lang.newLexParser().tryParse(expr, p -> p.expression((Integer) 0, 0)).root());

    }
}
