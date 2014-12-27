package org.bychan.dynamic;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        LanguageBuilder<Integer> b = new LanguageBuilder<>();
        return b.named("simpleCalc").newToken().named("whitespace").matchesPattern("\\s+").ignoredWhenParsing()
                .newToken().named("plus").matchesString("+")
                .infixParseAs((previous, match, parser) -> previous + parser.subExpression())
                .newToken().named("multiplication").matchesString("*")
                .infixParseAs((previous, match, parser) -> previous * parser.subExpression())
                .newToken().named("integer").matchesPattern("[0-9]+")
                .standaloneParseAs((previous, match) -> Integer.parseInt(match.getText()))
                .completeLanguage();
    }
}
