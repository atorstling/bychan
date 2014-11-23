package com.torstling.tdop.fluid.json;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.core.ParsingFailedInformation;
import org.jetbrains.annotations.NotNull;
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
        BnfGrammarBuilder g = b.newGrammar();
        b.define("boolean")
                .as("true", (x, y) -> new BooleanLiteral(true))
                .or()
                .as("false", (x, y) -> new BooleanLiteral(false)).addTo(g);
        assertEquals(new BooleanLiteral(true), g.parser().parse("true"));
        assertEquals(new BooleanLiteral(false), g.parser().parse("false"));
    }

    @Test
    public void nu11() {
        BnfBuilder b = new BnfBuilder();
        BnfGrammarBuilder g = b.newGrammar();
        b.define("null")
                .as("null", (x, y) -> NullLiteral.get()).addTo(g);
        assertEquals(NullLiteral.get(), g.parser().parse("null"));
    }

    @Test
    public void digitNotZero() {
        BnfBuilder b = new BnfBuilder();
        BnfGrammarBuilder g = b.newGrammar();
        makeDigitNotZero(b).addTo(g);
        assertEquals(new Digit(1), g.parser().parse("1"));
        ParsingFailedInformation failedInformation = new ParsingFailedInformation("Lexing failed:No matching rule for char-range starting at 0: '0'", new LexingMatch(0, 0, "0"));
        ParseResult<Object> failure = ParseResult.failure(failedInformation);
        assertEquals(failure, g.parser().tryParse("0"));
    }

    private NonTerminal makeDigitNotZero(@NotNull BnfBuilder b) {
        return b.define("digitNotZero")
                .as("[1-9]", (x, m) -> new Digit(Short.valueOf(m.getText()))).build();
    }

    @Test
    public void digitZero() {
        BnfBuilder b = new BnfBuilder();
        BnfGrammarBuilder g = b.newGrammar();
        makeDigitZero(b).addTo(g);
        assertEquals(new Digit(0), g.parser().parse("0"));
        ParsingFailedInformation failedInformation = new ParsingFailedInformation("Lexing failed:No matching rule for char-range starting at 0: '1'", new LexingMatch(0, 0, "1"));
        ParseResult<Object> failure = ParseResult.failure(failedInformation);
        assertEquals(failure, g.parser().tryParse("1"));
    }

    @NotNull
    private NonTerminal makeDigitZero(@NotNull final BnfBuilder b) {
        return b.define("digitZero")
                .as("0", (x, m) -> new Digit(Short.valueOf(m.getText()))).build();
    }

    @Test
    public void digit() {
        BnfBuilder b = new BnfBuilder();
        BnfGrammarBuilder g = b.newGrammar();
        b.define("digit").as(makeDigitZero(b)).or().as(makeDigitNotZero(b)).addTo(g);
        assertEquals(new Digit(1), g.parser().parse("1"));
        assertEquals(new Digit(0), g.parser().parse("0"));
    }
}
