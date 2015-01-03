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
    private final DynamicTokenFinder<N> tokenFinder;
    @Nullable
    private final DynamicInfixParseAction<N> infixBuilder;
    @Nullable
    private final DynamicPrefixParseAction<N> prefixBuilder;

    public DynamicLexeme(@NotNull final DynamicToken<N> token, @NotNull final LexingMatch match, @NotNull final TokenDefinition<N> def, @NotNull final DynamicTokenFinder<N> tokenFinder) {
        this.token = token;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
        infixBuilder = def.getInfixBuilder();
        prefixBuilder = def.getPrefixBuilder();
    }

    @Nullable
    @Override
    public NudParseAction<N> getPrefixParser() {
        return prefixBuilder == null ? null : (previous, parser) -> {
            UserParserCallbackImpl<N> callback = new UserParserCallbackImpl<>(leftBindingPower(), tokenFinder, parser, previous);
            return prefixBuilder.parse(previous, match, callback, leftBindingPower());
        };
    }

    @Nullable
    @Override
    public LedParseAction<N> getInfixParser() {
        return infixBuilder == null ? null : (previous, parser) -> {
            UserParserCallbackImpl<N> callback = new UserParserCallbackImpl<>(leftBindingPower(), tokenFinder, parser, previous);
            return infixBuilder.parse(previous, match, callback, leftBindingPower());
        };
    }

    @Override
    public int leftBindingPower() {
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
