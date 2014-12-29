package org.bychan.core.langs.calculator.manual;

import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class MultiplicationTokenType implements TokenType<CalculatorNode> {
    private static final MultiplicationTokenType INSTANCE = new MultiplicationTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new MultiplicationToken(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\*");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static  MultiplicationTokenType get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
