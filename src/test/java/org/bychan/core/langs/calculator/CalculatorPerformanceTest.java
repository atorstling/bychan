package org.bychan.core.langs.calculator;

import org.bychan.core.dynamic.Language;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class CalculatorPerformanceTest {

    @Test
    public void longAddition() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        String calculation = IntStream.range(0, 3000).boxed().map(Object::toString).collect(Collectors.joining("+"));
        Integer result = l.newLexParser().tryParse(calculation, p -> p.expr(null, 0)).root();
        assertEquals((Integer) 4498500, result);
    }
}
