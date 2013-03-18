package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.List;

public class SubtractionTokenTypes {
    @NotNull
    public static List<TokenType> get() {
        return Arrays.asList(
                NumberTokenType.get(),
                SubtractionTokenType.get(),
                AdditionTokenType.get(),
                MultiplicationTokenType.get(),
                LeftParenthesisTokenType.get(),
                RightParenthesisTokenType.get());
    }
}
