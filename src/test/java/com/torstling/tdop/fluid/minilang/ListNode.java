package com.torstling.tdop.fluid.minilang;

import com.torstling.tdop.utils.CollectionUtils;
import com.torstling.tdop.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListNode implements LaiLaiNode {
    private final List<LaiLaiNode> expressions;

    public ListNode(List<LaiLaiNode> expressions) {
        this.expressions = expressions;
    }

    @NotNull
    @Override
    public Object evaluate() {
        ArrayList<Object> results = new ArrayList<>(expressions.size());
        for (LaiLaiNode expression : expressions) {
            results.add(expression.evaluate());
        }
        return results;
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.LIST;
    }

    @Override
    public String toString() {
        Collection<String> strings = CollectionUtils.transform(expressions, CollectionUtils.toStringFunction());
        return "(l " + StringUtils.join(strings, " ") + " )";
    }
}
