package com.torstling.bychan.fluid;

import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenParserCallback;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GenericToken<N> implements Token<N> {
    private final GenericTokenType<N> tokenType;
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;

    public GenericToken(@NotNull final GenericTokenType<N> tokenType, @NotNull final LexingMatch match, @NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.tokenType = tokenType;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public N prefixParse(@Nullable N previous, @NotNull final TokenParserCallback<N> parser) {
        final PrefixAstBuilder<N> builder = def.getPrefixBuilder();
        if (builder == null) {
            throw new IllegalStateException("Prefix parsing not registered for token type: '" + toString() + "'");
        }
        return builder.build(previous, match, new ParserCallback2Impl<>(infixBindingPower(), tokenFinder, parser, previous));
    }

    @NotNull
    @Override
    public N infixParse(@Nullable final N previous, @NotNull final TokenParserCallback<N> parser) {
        InfixAstBuilder<N> infixBuilder = def.getInfixBuilder();
        if (infixBuilder == null) {
            throw new IllegalStateException("Definition does not support infix parsing: " + this);
        }
        return infixBuilder.build(match, previous, new ParserCallback2Impl<>(infixBindingPower(), tokenFinder, parser, previous));
    }

    @Override
    public int infixBindingPower() {
        return def.getLevel();
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
