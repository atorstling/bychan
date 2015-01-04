package org.bychan.core.dynamic;

import org.bychan.core.basic.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface LexemeInfo {
    @NotNull
    public LexingMatch getMatch();

    public int leftBindingPower();
}
