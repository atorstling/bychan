package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.langs.calculator.CalculatorTestHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReplTest {

    @Test
    public void normalUsage() throws InterruptedException, IOException, TimeoutException, ExecutionException {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        String expected = "Welcome to the REPL for 'simpleCalc'.\n" +
                "End with an empty line or Ctrl+D.\n" +
                // prompt for 2*3+5
                ">" +
                // prompt for empty line
                ">" +
                // result
                "11\n" +
                // prompt for quit
                ">" +
                // prompt for empty line
                ">" +
                // message leaving
                "leaving\n";
        BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("2*3+5").thenReturn("").thenReturn("quit").thenReturn("");
        check(l, in, expected);
    }

    @Test
    public void error() throws InterruptedException, IOException, TimeoutException, ExecutionException {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        String expected = "Welcome to the REPL for 'simpleCalc'.\n" +
                "End with an empty line or Ctrl+D.\n" +
                ">>" +
                "Error:Lexing failed: 'No matching rule' @  position 1:5 (index 4): last lexeme was 'plus(+)', remaining text is 'jocke'\n" +
                ">>" +
                "leaving\n";
        BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("2*3+jocke").thenReturn("").thenReturn("quit").thenReturn("");
        check(l, in, expected);
    }

    private void check(@NotNull Language<Integer> l, @NotNull BufferedReader in, @NotNull String expected) throws InterruptedException, TimeoutException, ExecutionException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Repl<Integer> r = new ReplBuilder<>(l).withIn(in).withOut(out).build();
        CompletableFuture.runAsync(r).get(1, TimeUnit.SECONDS);
        assertEquals(expected, out.toString());
    }

    static class TestException1 extends RuntimeException {
    }

    @Test
    public void propagateExceptionDuringParse() throws Exception {
        final LanguageBuilder<Integer> b = new LanguageBuilder<>("test");
        b.newToken().named("only").matchesString("a").nud((left, parser, lexeme) -> {
            throw new TestException1();
        }).build();
        final Language<Integer> l = b.completeLanguage();
        final BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("a").thenReturn("").thenReturn("quit").thenReturn("");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Repl<Integer> r = new ReplBuilder<>(l).withIn(in).withOut(out).build();
        try {
            r.run();
            fail("expected exception");
        } catch (TestException1 te) {
            //expected
        }
        assertEquals("Welcome to the REPL for 'test'.\n" +
                "End with an empty line or Ctrl+D.\n" +
                ">>", out.toString());
    }

    static class TestException2 extends RuntimeException {
    }

    static class Test2 {
        public void evaluate() {
            throw new TestException2();
        }
    }

    @Test
    public void propagateExceptionDuringEvaluate() throws Exception {
        final LanguageBuilder<Test2> b = new LanguageBuilder<>("test");
        b.newToken().named("only").matchesString("a").nud((left, parser, lexeme) -> {
            return new Test2();
        }).build();
        final Language<Test2> l = b.completeLanguage();
        final BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("a").thenReturn("").thenReturn("quit").thenReturn("");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Repl<Test2> r = new ReplBuilder<>(l).withIn(in).withOut(out).build();
        try {
            r.run();
            fail("expected exception");
        } catch (TestException2 te2) {
            //expected
        }
        assertEquals("Welcome to the REPL for 'test'.\n" +
                "End with an empty line or Ctrl+D.\n" +
                ">>", out.toString());
    }


    static class TestException3 extends RuntimeException {
    }

    static class Test3 {
        public void evaluate() {
            throw new TestException2();
        }
    }

    @Test
    public void canRecoverFromExceptionInParse() throws Exception {
        final LanguageBuilder<Integer> b = new LanguageBuilder<>("test");
        b.newToken().named("an a").matchesString("a").nud((left, parser, lexeme) -> {
            throw new TestException3();
        }).build();
        final Language<Integer> l = b.completeLanguage();
        final BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("a").thenReturn("").thenReturn("quit").thenReturn("");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Repl<Integer> r = new ReplBuilder<>(l)
                .withIn(in)
                .withOut(out)
                .withParsingFunction((lexParser, snippet) -> {
                    final Lexer<Integer> lexer = lexParser.getLexer();
                    LexingResult<Integer> lexingResult = lexer.tryLex(snippet);
                    if (lexingResult.isFailure()) {
                        LexingFailedInformation lexParsingFailedInformation = lexingResult.getFailureValue();
                        return ParseResult.failure(lexParsingFailedInformation);
                    }
                    final PrattParser<Integer> parser = new PrattParser<>(lexingResult.getSuccessValue(), snippet);
                    try {
                        return ParseResult.success(parser.parseExpression());
                    } catch (TestException3 te3) {
                        return ParseResult.failure(new ParsingFailedInformation("Exception3 thrown", parser.getParsingPosition()));
                    }
                })
                .build();
        r.run();
        assertEquals("Welcome to the REPL for 'test'.\n" +
                "End with an empty line or Ctrl+D.\n" +
                ">>Error:Parsing failed: 'Exception3 thrown' @  position 1:1 (index 0), current lexeme is an a(a), previous was null, and remaining are [END]\n" +
                ">>leaving\n", out.toString());
    }

}