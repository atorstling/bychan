package org.bychan.core.langs.calculator.manual;

import org.bychan.core.RegexMatcher;
import org.bychan.core.TokenMatcher;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;

public class AdditionToken implements Token<CalculatorNode> {
    private static final AdditionToken INSTANCE = new AdditionToken();

    @NotNull
    public Lexeme<CalculatorNode> toLexeme(@NotNull LexingMatch match) {
        return new AdditionLexeme(match);
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return new RegexMatcher("\\+");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public static AdditionToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
