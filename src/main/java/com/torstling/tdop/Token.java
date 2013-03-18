package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

interface Token {
    CalculatorNode prefixParse(@NotNull TokenParserCallback parser);
    CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser);
    /**
     * @return How strongly this token, when interpreted as an infix operator, binds to the left argument.
     */
    int infixBindingPower();
}
