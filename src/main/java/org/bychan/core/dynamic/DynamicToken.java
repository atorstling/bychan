package org.bychan.core.dynamic;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DynamicToken<N> implements Token<N> {
    private final DynamicTokenType<N> tokenType;
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final TokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;
    @Nullable
    private final DynamicInfixParseAction<N> infixBuilder;
    @Nullable
    private final DynamicPrefixParseAction<N> prefixBuilder;

    public DynamicToken(@NotNull final DynamicTokenType<N> tokenType, @NotNull final LexingMatch match, @NotNull final TokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.tokenType = tokenType;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
        infixBuilder = def.getInfixBuilder();
        prefixBuilder = def.getPrefixBuilder();
    }

    @Nullable
    @Override
    public PrefixParseAction<N> getPrefixParser() {
        return prefixBuilder == null ? null : (previous, parser) -> {
            UserParserCallbackImpl<N> callback = new UserParserCallbackImpl<>(leftBindingPower(), tokenFinder, parser, previous);
            return prefixBuilder.parse(previous, match, callback);
        };
    }

    @Nullable
    @Override
    public InfixParseAction<N> getInfixParser() {
        return infixBuilder == null ? null : (previous, parser) -> {
            UserParserCallbackImpl<N> callback = new UserParserCallbackImpl<>(leftBindingPower(), tokenFinder, parser, previous);
            return infixBuilder.parse(previous, match, callback);
        };
    }

    @Override
    public int leftBindingPower() {
        return def.getLeftBindingPower();
    }

    @Override
    @NotNull
    public TokenType<N> getType() {
        return tokenType;
    }

    public String toString() {
        return tokenType.getTokenDefinition().getTokenTypeName() + "(" + match.getText() + ")";
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }

}
