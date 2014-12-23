package org.bychan.fluid;

public class CalculatorTestHelper {
    public static Language<Integer> getSimpleCalculatorLanguage() {
        FluidLanguageBuilder<Integer> b = new FluidLanguageBuilder<>();
        return b
                .named("simpleCalc")
                .newLevelToken().named("whitespace").matchesPattern("\\s+").ignoreWhenParsing()
                .newLevelToken().named("plus").matchesString("+")
                .infixParseAs((match, previous, parser) -> previous + parser.subExpression())
                .newLevelToken().named("multiplication").matchesString("*")
                .infixParseAs((match, previous, parser) -> previous * parser.subExpression())
                .newLevelToken().named("integer").matchesPattern("[0-9]+")
                .standaloneParseAs((previous, match) -> Integer.parseInt(match.getText()))
                .completeLanguage();
    }
}
