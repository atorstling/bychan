package org.bychan.core;

import org.bychan.generic.CalculatorTestHelper;
import org.bychan.generic.Language;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReplTest {

    @Test
    public void normalUsage() throws InterruptedException, IOException {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        String expected = "welcome to the REPL for 'simpleCalc'\n" +
                ">11\n" +
                ">leaving";
        BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("2*3+5").thenReturn("quit");
        check(l, in, expected);
    }

    @Test
    public void error() throws InterruptedException, IOException {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        String expected = "welcome to the REPL for 'simpleCalc'\n" +
                ">Error:Lexing failed: 'No matching rule for char-range starting at 4: 'jocke'' @ LexingPosition{streamPosition=4, remainingText='jocke'}\n" +
                ">leaving";
        BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("2*3+jocke").thenReturn("quit");
        check(l, in, expected);
    }

    private void check(@NotNull Language<Integer> l, @NotNull BufferedReader in, @NotNull String expected) throws InterruptedException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Repl<Integer> r = new Repl<>(l, in, new BufferedWriter(new OutputStreamWriter(out)));
        ExecutorService e = Executors.newSingleThreadExecutor();
        e.submit(r);
        e.shutdown();
        e.awaitTermination(1, TimeUnit.SECONDS);
        assertEquals(expected, out.toString());
    }

}