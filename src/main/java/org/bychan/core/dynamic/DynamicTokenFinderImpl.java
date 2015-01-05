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
    private final Map<String, DynamicToken<N>> tokensByName;

    public DynamicTokenFinderImpl(Collection<DynamicToken<N>> dynamicTokens) {
        tokensByName = dynamicTokens.stream().collect(Collectors.toMap(DynamicToken::getName, Function.identity()));

    }

    @NotNull
    @Override
    public DynamicToken<N> getToken(@NotNull String soughtName) {
        DynamicToken<N> candidate = tokensByName.get(soughtName);
        if (candidate == null) {
            throw new IllegalStateException("No registered token definition nameed '" + soughtName + "' was found. Did you register your token before referring to it?");
        } else if (candidate.getTokenName().equals(soughtName)) {
            return candidate;
        } else {
            throw new IllegalStateException("Found a candidate token definition with the same name ('" + soughtName + "'), but it had a different specification. Do you have multiple copies of this token definition?");
        }
    }
}
