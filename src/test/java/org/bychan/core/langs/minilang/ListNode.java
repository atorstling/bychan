package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final List<LaiLaiNode> expressions;

    public ListNode(@NotNull LaiLaiNode left, @NotNull final List<LaiLaiNode> expressions) {
        this.left = left;
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
        return left.getScope();
    }

    @Override
    public String toString() {
        return "(l " + expressions.stream().map(LaiLaiNode::toString).collect(Collectors.joining(" ")) + " )";
    }
}
