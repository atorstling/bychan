package org.bychan.core.langs.calculator.manual;

import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class AdditionTokenType<S> implements TokenType<CalculatorNode> {
    private static final AdditionTokenType INSTANCE = new AdditionTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new AdditionToken(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\+");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <S> AdditionTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
