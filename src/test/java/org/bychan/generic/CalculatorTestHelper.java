package org.bychan.generic;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        LanguageBuilder<Integer> b = new LanguageBuilder<>();
        return b.named("simpleCalc").newToken().named("whitespace").matchesPattern("\\s+").ignoredWhenParsing()
                .newToken().named("plus").matchesString("+")
                .infixParseAs((match, previous, parser) -> previous + parser.subExpression())
                .newToken().named("multiplication").matchesString("*")
                .infixParseAs((match, previous, parser) -> previous * parser.subExpression())
                .newToken().named("integer").matchesPattern("[0-9]+")
                .standaloneParseAs((previous, match) -> Integer.parseInt(match.getText()))
                .completeLanguage();
    }
}
