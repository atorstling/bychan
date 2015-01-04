package org.bychan.core.basic;

import org.bychan.core.dynamic.CalculatorTestHelper;
import org.bychan.core.dynamic.Language;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
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
                ">Error:Lexing failed: 'No matching rule' @  position 1:5 (index 4), remaining text is 'jocke'\n" +
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