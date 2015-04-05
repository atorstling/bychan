package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2015-04-05.
 */
public interface FailureInformation {
    @NotNull
    TextPosition getTextPosition();

    @NotNull
    ParsingFailedInformation toParsingFailedInformation();
}
