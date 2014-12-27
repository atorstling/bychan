package org.bychan.generic;

import org.bychan.calculator.nodes.CalculatorNode;
import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WhitespaceToken<N> implements Token<N> {
    private final WhitespaceTokenType<N> type;
    @NotNull
    private final LexingMatch match;

    public WhitespaceToken(WhitespaceTokenType<N> type, @NotNull final LexingMatch match) {
        this.type = type;
        this.match = match;
    }

    @Nullable
    @Override
    public PrefixParser<N> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public InfixParser<N> getInfixParser() {
        return null;
    }

    @Override
    public int leftBindingPower() {
        return 0;
    }

    @NotNull
    @Override
    public TokenType<N> getType() {
        return type;
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
