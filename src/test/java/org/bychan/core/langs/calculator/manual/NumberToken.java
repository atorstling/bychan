package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class NumberToken implements Token<CalculatorNode> {
    private static final NumberToken INSTANCE = new NumberToken();

    @NotNull
    public Lexeme<CalculatorNode> toLexeme(@NotNull LexingMatch match) {
        return NumberLexeme.valueOf(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\d+");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static NumberToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
