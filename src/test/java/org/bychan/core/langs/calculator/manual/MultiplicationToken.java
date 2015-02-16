package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.dynamic.RegexMatcher;
import org.bychan.core.dynamic.TokenMatcher;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;

public class MultiplicationToken implements Token<CalculatorNode> {
    private static final MultiplicationToken INSTANCE = new MultiplicationToken();

    public static MultiplicationToken get() {
        //noinspection unchecked
        return INSTANCE;
    }

    @NotNull
    public Lexeme<CalculatorNode> toLexeme(@NotNull LexingMatch match) {
        return new MultiplicationLexeme(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("\\*");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }
}
