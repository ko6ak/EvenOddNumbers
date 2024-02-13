package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class EvenOddNumbersPrinterTest {

    @Test
    void print(){
        PrintStream stdout = System.out;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteStream));

        new EvenOddNumbersPrinter(4).print();

        System.setOut(stdout);

        String expected = """
                Even: 0\r
                Odd : 1\r
                Even: 2\r
                Odd : 3\r
                """;

        Assertions.assertEquals(expected, byteStream.toString());
    }

    @Test
    void printZero(){
        PrintStream stdout = System.out;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteStream));

        new EvenOddNumbersPrinter(0).print();

        System.setOut(stdout);

        Assertions.assertEquals(0, byteStream.size());
    }
}
