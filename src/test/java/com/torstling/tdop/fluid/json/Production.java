package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.StandaloneAstBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/26/14.
 */
public interface Production {
    @NotNull
    String getName();

    @NotNull
    String getLexerPattern();

    @NotNull
    StandaloneAstBuilder<Object> getAstBuilder();
}
