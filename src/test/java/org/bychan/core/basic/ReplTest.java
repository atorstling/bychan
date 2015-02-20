package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;
import org.bychan.core.langs.calculator.CalculatorTestHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
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
        Repl<Integer> r = new Repl<>(l, in, new BufferedWriter(new OutputStreamWriter(out)));
        CompletableFuture.runAsync(r).get(1, TimeUnit.SECONDS);
        assertEquals(expected, out.toString());
    }

}