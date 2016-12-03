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
    private final Map<TokenKey, Token<N>> tokensByKey;

    public TokenFinderImpl(Collection<DynamicToken<N>> dynamicTokens) {
        tokensByKey = dynamicTokens.stream().collect(Collectors.toMap(DynamicToken::getKey, Function.identity()));
        tokensByKey.put(EndToken.get().getKey(), EndToken.get());
    }

    @NotNull
    @Override
    public Token<N> getToken(@NotNull TokenKey soughtKey) {
        Token<N> candidate = tokensByKey.get(soughtKey);
        if (candidate == null) {
            throw new IllegalStateException("No registered token definition keyed '" + soughtKey + "' was found. Did you register your token before referring to it?");
        } else if (candidate.getKey().equals(soughtKey)) {
            return candidate;
        } else {
            throw new IllegalStateException("Found a candidate token definition with the same key ('" + soughtKey + "'), but it had a different specification. Do you have multiple copies of this token definition?");
        }
    }
}
