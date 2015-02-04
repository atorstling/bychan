package org.bychan.core.langs.calculator.manual;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;

public class NumberToken implements Token<CalculatorNode> {
    private static final NumberToken INSTANCE = new NumberToken();

    @NotNull
    public Lexeme<CalculatorNode> toLexeme(@NotNull LexingMatch match) {
        return NumberLexeme.valueOf(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("\\d+");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public static NumberToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
