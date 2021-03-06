package org.bychan.core.dynamic;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DynamicLexeme<N> implements Lexeme<N> {
    private final DynamicToken<N> token;
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final TokenDefinition<N> def;
    @NotNull
    private final TokenFinder<N> tokenFinder;
    @Nullable
    private final DynamicLedParseAction<N> led;
    @Nullable
    private final DynamicNudParseAction<N> nud;

    public DynamicLexeme(@NotNull final DynamicToken<N> token, @NotNull final LexingMatch match, @NotNull final TokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.token = token;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
        led = def.getLed();
        nud = def.getNud();
    }

    @Nullable
    @Override
    public NudParseAction<N> getNud() {
        return nud == null ? null : (left, parser) -> nud.parse(left, parser, this);
    }

    @Nullable
    @Override
    public LedParseAction<N> getLed() {
        return led == null ? null : (left, parser) -> led.parse(left, parser, this);
    }

    @Override
    public int lbp() {
        return def.getLeftBindingPower();
    }

    @Override
    @NotNull
    public Token<N> getToken() {
        return token;
    }

    public String toString() {
        return token.getTokenDefinition().tokenName() + "(" + match.getText() + ")";
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }

}
