package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by alext on 2015-03-25.
 */
public class AnyPermutationMatcher implements TokenMatcher {
    @NotNull
    private final List<String> targets;
    @NotNull
    private final Collection<String> ignoredSeparators;
    @NotNull
    private final List<String> all;

    public AnyPermutationMatcher(@NotNull final List<String> targets, @NotNull final Collection<String> ignoredSeparators) {
        this.targets = targets;
        this.ignoredSeparators = ignoredSeparators;
        all = new ArrayList<>(targets);
        all.addAll(ignoredSeparators);
    }

    @Nullable
    @Override
    public TokenMatchResult tryMatch(@NotNull String input, final int searchStart) {
        ArrayList<String> targetMatches = new ArrayList<>();
        int currentPosition = searchStart;
        int totalProgress = 0;
        int progress;
        do {
            String targetMatch = findMatch(input, currentPosition, targets);
            String sepMatch = null;
            if (targetMatch != null) {
                targetMatches.add(targetMatch);
                sepMatch = findMatch(input, currentPosition + targetMatch.length(), ignoredSeparators);
            }
            int targetMatchLen = targetMatch == null ? 0 : targetMatch.length();
            int sepMatchLen = sepMatch == null ? 0 : sepMatch.length();
            progress = targetMatchLen + sepMatchLen;
            totalProgress += progress;
            currentPosition += progress;
        } while (progress != 0);
        Set<String> dupes = getDuplicates(targetMatches);
        if (!dupes.isEmpty()) {
            throw new IllegalStateException("Duplicates: " + dupes);
        }
        if (totalProgress == 0) {
            return null;
        }
        return TokenMatchResult.create(searchStart + totalProgress);
    }

    @NotNull
    private <T> LinkedHashSet<T> getDuplicates(@NotNull final Iterable<T> iterable) {
        LinkedHashSet<T> duplicates = new LinkedHashSet<>();
        Set<T> dupeChecker = new LinkedHashSet<>();
        iterable.forEach((m) -> {
            if (!dupeChecker.add(m)) {
                duplicates.add(m);
            }
        });
        return duplicates;
    }

    @Nullable
    private String findMatch(@NotNull final String input, final int searchStart, @NotNull final Iterable<String> candidates) {
        for (String candidate : candidates) {
            boolean match = matches(input, searchStart, candidate);
            if (match) {
                return candidate;
            }
        }
        return null;
    }

    private boolean matches(String input, int searchStart, String string) {
        return input.regionMatches(searchStart, string, 0, string.length());
    }
}
