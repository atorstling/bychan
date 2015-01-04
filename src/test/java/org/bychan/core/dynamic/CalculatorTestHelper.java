package org.bychan.core.dynamic;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        LanguageBuilder<Integer> b = new LanguageBuilder<>();
        return b.named("simpleCalc")
                .newToken().named("whitespace").matchesPattern("\\s+").ignoredWhenParsing().end()
                .newToken().named("plus").matchesString("+")
                .led((previous, parser, lexeme) -> previous + parser.subExpression()).end()
                .newToken().named("multiplication").matchesString("*")
                .led((previous, parser, lexeme) -> previous * parser.subExpression()).end()
                .newToken().named("integer").matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText())).end()
                .completeLanguage();
    }
}
