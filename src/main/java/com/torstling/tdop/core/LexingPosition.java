package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/26/14.
 */
public class LexingPosition {
    private final int streamPosition;
    @NotNull
    private final String matchSection;

    public LexingPosition(final int streamPosition, @NotNull final String matchSection) {
        this.streamPosition = streamPosition;
        this.matchSection = matchSection;
    }

    public int getStreamPosition() {
        return streamPosition;
    }

    @NotNull
    public String getMatchSection() {
        return matchSection;
    }
}
