package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by alext on 2015-01-05.
 */
class DynamicTokenFinderImpl<N> implements DynamicTokenFinder<N> {
    private final Map<TokenKey, DynamicToken<N>> tokensByKey;

    public DynamicTokenFinderImpl(Collection<DynamicToken<N>> dynamicTokens) {
        tokensByKey = dynamicTokens.stream().collect(Collectors.toMap(DynamicToken::getKey, Function.identity()));

    }

    @NotNull
    @Override
    public DynamicToken<N> getToken(@NotNull TokenKey soughtKey) {
        DynamicToken<N> candidate = tokensByKey.get(soughtKey);
        if (candidate == null) {
            throw new IllegalStateException("No registered token definition keyed '" + soughtKey + "' was found. Did you register your token before referring to it?");
        } else if (candidate.getKey().equals(soughtKey)) {
            return candidate;
        } else {
            throw new IllegalStateException("Found a candidate token definition with the same key ('" + soughtKey + "'), but it had a different specification. Do you have multiple copies of this token definition?");
        }
    }
}
