package org.bychan.core.dynamic;

import org.bychan.core.utils.CollectionUtils;
import org.bychan.core.utils.PatternUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnyPermutationMatcher implements TokenMatcher {
    @NotNull
    private final Pattern pattern;

    public AnyPermutationMatcher(@NotNull final List<String> targets, @NotNull final Collection<String> ignoredSeparators) {
        List<String> quotedSeparators = PatternUtils.quote(ignoredSeparators);
        List<String> quotedTargets = PatternUtils.quote(targets);
        String anySeparatorPatternString = String.join("|", quotedSeparators);
        String anyTargetPatternString = String.join("|", quotedTargets);
        pattern = Pattern.compile("(" + anyTargetPatternString + ")(?:" + anySeparatorPatternString + ")?");
    }

    @Nullable
    @Override
    public TokenMatchResult tryMatch(@NotNull String input, final int searchStart) {
        ArrayList<String> hits = new ArrayList<>();
        Matcher m = pattern.matcher(input);
        m.region(searchStart, input.length());
        while (m.lookingAt()) {
            String hit = m.group(1);
            hits.add(hit);
            m.region(m.end(), input.length());
        }
        if (hits.isEmpty()) {
            return null;
        }
        Set<String> dupes = CollectionUtils.getDuplicates(hits);
        if (!dupes.isEmpty()) {
            throw new IllegalStateException("Duplicates: " + dupes);
        }
        return TokenMatchResult.create(m.regionStart());
    }

}
