package org.bychan.core.dynamic;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        LanguageBuilder<Integer> b = new LanguageBuilder<>();
        b.named("simpleCalc");
        b.newToken().named("whitespace").matchesPattern("\\s+").ignoredWhenParsing().build();
        b.newToken().named("plus").matchesString("+")
                .led((previous, parser, lexeme) -> previous + parser.subExpression(previous)).build();
        b.newToken().named("multiplication").matchesString("*")
                .led((previous, parser, lexeme) -> previous * parser.subExpression(previous)).build();
        b.newToken().named("integer").matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText())).build();
        return b.completeLanguage();
    }
}
