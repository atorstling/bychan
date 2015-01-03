package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class SubtractionToken implements Token<CalculatorNode> {
    private static final SubtractionToken INSTANCE = new SubtractionToken();

    @NotNull
    public Lexeme<CalculatorNode> toLexeme(@NotNull LexingMatch match) {
        return new SubtractionLexeme(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("-");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static SubtractionToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
