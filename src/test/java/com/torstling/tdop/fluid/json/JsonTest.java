package com.torstling.tdop.fluid.json;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.core.ParsingFailedInformation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
        b.add("null = 'null'");
        b.add("zero = '0'");
        b.add("digit_not_zero = '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'");
        b.add("digit = zero | digit_not_zero");
        b.add("number = ['-'], zero | (digit_not_zero, {digit}), ['.', {digit})], ['e' | 'E',  ['+' | '-'], {digit}]");
                "value = string | number | object | array | boolean | null" +
                "object = '{', { string : value } ,'}'");
        */
public class JsonTest {

    @Test
    public void bool() {
        BnfBuilder b = new BnfBuilder();
        b.define("boolean")
                .as("true", (x, y) -> new BooleanLiteral(true))
                .or()
                .as("false", (x, y) -> new BooleanLiteral(false));
        assertEquals(new BooleanLiteral(true), b.parser().parse("true"));
        assertEquals(new BooleanLiteral(false), b.parser().parse("false"));
    }

    @Test
    public void nu11() {
        BnfBuilder b = new BnfBuilder();
        b.define("null")
                .as("null", (x, y) -> NullLiteral.get());
        assertEquals(NullLiteral.get(), b.parser().parse("null"));
    }

    @Test
    public void digitNotZero() {
        BnfBuilder b = new BnfBuilder();
        b.define("digitNotZero")
                .as("[1-9]", (Object x, LexingMatch y) -> new NonNegativeDigitLiteral(Short.valueOf(y.getText())));
        assertEquals(new NonNegativeDigitLiteral(1), b.parser().parse("1"));
        assertEquals(ParseResult.failure(new ParsingFailedInformation("Lexing failed:No matching rule for char-range starting at 0: '0'", new LexingMatch(0, 0, "0"))), b.parser().tryParse("0"));
    }
}
