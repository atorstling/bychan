package org.bychan.core.dynamic;

import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class CalculatorPerformanceTest {

    @Test
    public void longAddition() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        String calculation = IntStream.range(0, 3000).boxed().map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        }).collect(Collectors.joining("+"));
        Integer result = l.getLexParser().parse(calculation);
        assertEquals((Integer) 4498500, result);
    }
}
