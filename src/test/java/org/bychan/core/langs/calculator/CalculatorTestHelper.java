package org.bychan.core.langs.calculator;

import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        LanguageBuilder<Integer> b = new LanguageBuilder<>("simpleCalc");
        b.newToken().named("whitespace").matchesPattern("\\s+").discardAfterLexing()
                .build();
        b.powerUp();
        b.newToken().named("plus").matchesString("+")
                .led((left, parser, lexeme) -> left + parser.parseExpression(left, lexeme.leftBindingPower()))
                .build();
        b.powerUp();
        b.newToken().named("multiplication").matchesString("*")
                .led((left, parser, lexeme) -> left * parser.parseExpression(left, lexeme.leftBindingPower()))
                .build();
        b.powerUp();
        b.newToken().named("integer").matchesPattern("[0-9]+")
                .nud((left, parser, lexeme) -> Integer.parseInt(lexeme.getText()))
                .build();
        return b.completeLanguage();
    }
}
