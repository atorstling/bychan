package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.langs.calculator.CalculatorTestHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReplTest {

    final String prompt = "End a statement by adding an empty line. Quit by entering 'q', 'quit' or 'end' or pressing Ctrl+D.\n";

    @Test
    public void normalUsage() throws InterruptedException, IOException, TimeoutException, ExecutionException {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        String expected = "Welcome to the REPL for 'simpleCalc'.\n" +
                prompt +
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
                prompt +
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
        Repl<Integer> r = new ReplBuilder<>(l, p -> p.expr(null, 0)).withIn(in).withOut(out).build();
        r.run();
        assertEquals(replaceNewlines(expected), out.toString());
    }

    private String replaceNewlines(String s) {
        return s.replace("\n", System.lineSeparator());
    }

    static class TestException1 extends RuntimeException {
    }

    @Test
    public void propagateExceptionDuringParse() throws Exception {
        final LanguageBuilder<Integer> b = new LanguageBuilder<>("test");
        b.newToken().named("only").matchesString("a").nud((left, parser, lexeme) -> {
            throw new TestException1();
        }).build();
        final Language<Integer> l = b.build();
        final BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("a").thenReturn("").thenReturn("quit").thenReturn("");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Repl<Integer> r = new ReplBuilder<>(l, p -> p.expr(null, 0)).withIn(in).withOut(out).build();
        try {
            r.run();
            fail("expected exception");
        } catch (TestException1 te) {
            //expected
        }
        assertEquals(replaceNewlines("Welcome to the REPL for 'test'.\n" +
                prompt +
                ">>"), out.toString());
    }

    static class TestException2 extends RuntimeException {
    }

    static class Test2 implements Evaluatable {
        public Object evaluate() {
            throw new TestException2();
        }
    }

    @Test
    public void propagateExceptionDuringEvaluate() throws Exception {
        final LanguageBuilder<Test2> b = new LanguageBuilder<>("test");
        b.newToken().named("only").matchesString("a").nud((left, parser, lexeme) -> new Test2()).build();
        final Language<Test2> l = b.build();
        final BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("a").thenReturn("").thenReturn("quit").thenReturn("");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Repl<Test2> r = new ReplBuilder<>(l, p -> p.expr(null, 0)).withIn(in).withOut(out).build();
        try {
            r.run();
            fail("expected exception");
        } catch (TestException2 te2) {
            //expected
        }
        assertEquals(replaceNewlines("Welcome to the REPL for 'test'.\n" +
                prompt +
                ">>"), out.toString());
    }


    static class TestException3 extends RuntimeException {
    }

    @Test
    public void canRecoverFromExceptionInParse() throws Exception {
        final LanguageBuilder<Integer> b = new LanguageBuilder<>("test");
        b.newToken().named("an a").matchesString("a").nud((left, parser, lexeme) -> {
            throw new TestException3();
        }).build();
        final Language<Integer> l = b.build();
        final BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("a").thenReturn("").thenReturn("quit").thenReturn("");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Repl<Integer> r = new ReplBuilder<>(l, p -> p.expr(null, 0))
                .withIn(in)
                .withOut(out)
                .withRunFunction((lexParser, parseFunction, snippet) -> {
                    try {
                        final ParseResult<Integer> result = lexParser.tryParse(snippet, parseFunction);
                        if (result.isFailure()) {
                            return ReplRunResult.error(result.getErrorMessage().toString());
                        }
                        return ReplRunResult.success(result.root());
                    } catch (TestException3 te3) {
                        return ReplRunResult.error("Exception3 thrown");
                    }
                })
                .build();
        r.run();
        assertEquals(replaceNewlines("Welcome to the REPL for 'test'.\n" +
                prompt +
                ">>Error:Exception3 thrown\n" +
                ">>leaving\n"), out.toString());
    }

    static class TestException4 extends RuntimeException {
    }

    static class Test4 implements Evaluatable {
        public Object evaluate() {
            throw new TestException4();
        }

        @Override
        public String toString() {
            return "Test4";
        }
    }

    @Test
    public void canRecoverFromExceptionDuringEvaluate() throws Exception {
        final LanguageBuilder<Test4> b = new LanguageBuilder<>("test");
        b.newToken().named("an a").matchesString("a").nud((left, parser, lexeme) -> new Test4()).build();
        final Language<Test4> l = b.build();
        final BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("a").thenReturn("").thenReturn("quit").thenReturn("");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Repl<Test4> r = new ReplBuilder<>(l, p -> p.expr(null, 0))
                .withIn(in)
                .withOut(out)
                .withEvaluationFunction(node -> {
                    try {
                        return Repl.invokeEvaluate(node);
                    } catch (TestException4 te4) {
                        return null;
                    }
                })
                .build();
        r.run();
        assertEquals(replaceNewlines("Welcome to the REPL for 'test'.\n" +
                prompt +
                ">>Test4\n" +
                ">>leaving\n"), out.toString());
    }

}