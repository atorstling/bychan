package org.bychan.core.langs.calculator.manual;

import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class NumberTokenType<S> implements TokenType<CalculatorNode> {
    private static final NumberTokenType INSTANCE = new NumberTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return NumberToken.valueOf(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\d+");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <S> NumberTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
