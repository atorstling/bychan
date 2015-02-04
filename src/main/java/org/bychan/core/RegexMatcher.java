package org.bychan.core;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alext on 2015-02-04.
 */
public class RegexMatcher implements TokenMatcher {
    @NotNull
    private final Pattern pattern;

    public RegexMatcher(@NotNull final Pattern pattern) {
        this.pattern = pattern;
    }

    public RegexMatcher(@NotNull final String pattern) {
        this(Pattern.compile(pattern));
    }

    public Matcher matcher(@NotNull final String text) {
        return pattern.matcher(text);
    }

    @Override
    public int tryMatch(@NotNull String input, int searchStart) {
        Matcher matcher = pattern.matcher(input);
        matcher.region(searchStart, input.length());
        if (matcher.lookingAt()) {
            return matcher.end();
        }
        return -1;
    }
}
