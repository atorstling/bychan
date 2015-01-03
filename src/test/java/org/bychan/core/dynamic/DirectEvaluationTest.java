package org.bychan.core.dynamic;

import org.bychan.core.basic.Token;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DirectEvaluationTest {

    @Test
    public void directEvaluationCalculator() {
        LanguageBuilder<Integer> builder = new LanguageBuilder<>();
        TokenDefinition<Integer> number = builder.newToken()
                .named("number")
                .matchesPattern("[0-9]+").prefixParseAs((previous, match, parser, lbp) -> Integer.parseInt(match.getText()))
                .build();
        TokenDefinition<Integer> addition = builder.newToken()
                .named("addition")
                .matchesString("+")
                .infixParseAs((previous, match, parser, lbp) -> previous + parser.subExpression())
                .build();
        Language<Integer> lang = builder
                .addToken(number)
                .addToken(addition)
                .completeLanguage();
        String expr = "1+3";
        List<Token<Integer>> tokens = lang.getLexer().lex(expr);
        assertEquals("[number(1), addition(+), number(3), END]", tokens.toString());
        assertEquals(4, (int) lang.getLexParser().tryParse(0, expr).getRootNode());

    }
}
