package org.bychan.core.dynamic;

import org.bychan.core.basic.EndToken;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by alext on 2015-01-05.
 */
class TokenFinderImpl<N> implements TokenFinder<N> {
    private final Map<String, Token<N>> tokensByKey;

    public TokenFinderImpl(Collection<DynamicToken<N>> dynamicTokens) {
        tokensByKey = dynamicTokens.stream().collect(Collectors.toMap(DynamicToken::getName, Function.identity()));
    }

    @NotNull
    @Override
    public Token<N> getToken(@NotNull String name) {
        Token<N> candidate = tokensByKey.get(name);
        if (candidate == null) {
            throw new IllegalStateException("No registered token definition named '" + name + "' was found. Did you register your token before referring to it?");
        } else if (candidate.getName().equals(name)) {
            return candidate;
        } else {
            throw new IllegalStateException("Found a candidate token definition with the same key ('" + name + "'), but it had a different specification. Do you have multiple copies of this token definition?");
        }
    }
}
