package org.bychan.fluid;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GenericToken<N> implements Token<N> {
    private final GenericTokenType<N> tokenType;
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;
    @Nullable
    private final InfixAstBuilder<N> infixBuilder;
    @Nullable
    private final PrefixAstBuilder<N> prefixBuilder;

    public GenericToken(@NotNull final GenericTokenType<N> tokenType, @NotNull final LexingMatch match, @NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.tokenType = tokenType;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
        infixBuilder = def.getInfixBuilder();
        prefixBuilder = def.getPrefixBuilder();
    }

    @Override
    public boolean supportsPrefixParsing() {
        return prefixBuilder != null;
    }

    @Override
    public boolean supportsInfixParsing() {
        return infixBuilder != null;
    }

    @NotNull
    @Override
    public N prefixParse(@Nullable N previous, @NotNull final TokenParserCallback<N> parser) {
        assert prefixBuilder != null;
        return prefixBuilder.build(previous, match, new FluidParserCallbackImpl<>(leftBindingPower(), tokenFinder, parser, previous));
    }

    @NotNull
    @Override
    public N infixParse(@Nullable final N previous, @NotNull final TokenParserCallback<N> parser) {
        assert infixBuilder != null;
        return infixBuilder.build(match, previous, new FluidParserCallbackImpl<>(leftBindingPower(), tokenFinder, parser, previous));
    }

    @Override
    public int leftBindingPower() {
        return def.getLevel() + 1;
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
