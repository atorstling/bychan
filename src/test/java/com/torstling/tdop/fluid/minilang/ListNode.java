package com.torstling.tdop.fluid.minilang;

import com.torstling.tdop.utils.Lists;
import com.torstling.tdop.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ListNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode previous;
    @NotNull
    private final List<LaiLaiNode> expressions;

    public ListNode(@NotNull LaiLaiNode previous, @NotNull final List<LaiLaiNode> expressions) {
        this.previous = previous;
        this.expressions = expressions;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        ArrayList<Object> results = new ArrayList<>(expressions.size());
        results.addAll(expressions.stream().map(expression -> expression.evaluate(currentScope)).collect(Collectors.toList()));
        return results;
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.LIST;
    }

    @Nullable
    @Override
    public Scope getScope() {
        return previous.getScope();
    }

    @Override
    public String toString() {
        Collection<String> strings = Lists.transform(expressions, Object::toString);
        return "(l " + StringUtils.join(strings, " ") + " )";
    }
}
