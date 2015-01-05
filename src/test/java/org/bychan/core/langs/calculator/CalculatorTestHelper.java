package org.bychan.core.langs.calculator;

import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        LanguageBuilder<Integer> b = new LanguageBuilder<>("simpleCalc");
        b.newToken().named("whitespace").matchesPattern("\\s+").discardAfterLexing()
                .build();
        b.newToken().named("plus").matchesString("+")
                .led((previous, parser, lexeme) -> previous + parser.expression(previous))
                .build();
        b.newToken().named("multiplication").matchesString("*")
                .led((previous, parser, lexeme) -> previous * parser.expression(previous))
                .build();
        b.newToken().named("integer").matchesPattern("[0-9]+")
                .nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText()))
                .build();
        return b.completeLanguage();
    }
}