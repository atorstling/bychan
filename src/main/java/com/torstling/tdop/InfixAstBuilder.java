package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 3/22/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InfixAstBuilder<N extends Node> {
    BooleanExpressionNode build(@NotNull BooleanExpressionNode left, @NotNull BooleanExpressionNode right);
}
