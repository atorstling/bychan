package org.bychan.core.basic;

/**
 * If you implement this interface the Repl will automatically call evaluate() on the root node to force a calculation
 * of your AST.
 */
public interface Evaluatable<T> {
    T evaluate();
}
