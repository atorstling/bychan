package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class MultiplicationToken implements Token<CalculatorNode> {
    private static final MultiplicationToken INSTANCE = new MultiplicationToken();

    @NotNull
    public Lexeme<CalculatorNode> toLexeme(@NotNull LexingMatch match) {
        return new MultiplicationLexeme(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\*");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public static MultiplicationToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
