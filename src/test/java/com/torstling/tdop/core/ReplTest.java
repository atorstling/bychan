package com.torstling.tdop.core;

import com.torstling.tdop.fluid.CalculatorTestHelper;
import com.torstling.tdop.fluid.Language;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReplTest {

    @Test
    public void normalUsage() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
    }

}