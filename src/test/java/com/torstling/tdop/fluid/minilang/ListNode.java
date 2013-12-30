package com.torstling.tdop.fluid.minilang;

import com.torstling.tdop.utils.CollectionUtils;
import com.torstling.tdop.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode parent;
    @NotNull
    private final List<LaiLaiNode> expressions;

    public ListNode(@NotNull final LaiLaiNode parent, @NotNull final List<LaiLaiNode> expressions) {
        this.parent = parent;
        this.expressions = expressions;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        ArrayList<Object> results = new ArrayList<>(expressions.size());
        for (LaiLaiNode expression : expressions) {
            results.add(expression.evaluate(currentScope));
        }
        return results;
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.LIST;
    }

    @Override
    public String toString() {
        Collection<String> strings = CollectionUtils.transform(expressions, CollectionUtils.toStringFunction());
        return "(l " + StringUtils.join(strings, " ") + " )";
    }
}
