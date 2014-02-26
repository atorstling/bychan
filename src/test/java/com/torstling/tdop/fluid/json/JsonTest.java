package com.torstling.tdop.fluid.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonTest {

    @Test
    public void bool() {
        BnfBuilder b = new BnfBuilder();
        b.define("boolean").as("true").or().as("false");
        Object o = b.parser().parse("true");
        assertEquals(new BooleanLiteral(true), o);
        /*
        b.add("null = 'null'");
        b.add("zero = '0'");
        b.add("digit_not_zero = '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'");
        b.add("digit = zero | digit_not_zero");
        b.add("number = ['-'], zero | (digit_not_zero, {digit}), ['.', {digit})], ['e' | 'E',  ['+' | '-'], {digit}]");
                "value = string | number | object | array | boolean | null" +
                "object = '{', { string : value } ,'}'");
        */
    }
}
