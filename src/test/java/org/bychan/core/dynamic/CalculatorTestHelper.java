package org.bychan.core.dynamic;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        LanguageBuilder<Integer> b = new LanguageBuilder<>();
        return b.named("simpleCalc")
                .newToken().named("whitespace").matchesPattern("\\s+").ignoredWhenParsing().end()
                .newToken().named("plus").matchesString("+")
                .infixParseAs((previous, match, parser, lbp) -> previous + parser.subExpression()).end()
                .newToken().named("multiplication").matchesString("*")
                .infixParseAs((previous, match, parser, lbp) -> previous * parser.subExpression()).end()
                .newToken().named("integer").matchesPattern("[0-9]+").prefixParseAs((previous, match, parser, lbp) -> Integer.parseInt(match.getText())).end()
                .completeLanguage();
    }
}
