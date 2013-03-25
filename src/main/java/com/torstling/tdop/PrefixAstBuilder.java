package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 3/22/13
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PrefixAstBuilder<N extends Node> {
    N build(@NotNull N trailingExpression);
}
