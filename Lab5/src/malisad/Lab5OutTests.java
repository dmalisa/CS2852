/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Example
 * Name: malisad
 * Created 4/6/2021
 */
package malisad;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * Lab5OutTests purpose: Tests the BufferedOutputStream implementation
 *
 * @author malisad
 * @version created on 4/6/2021 at 10:27 AM
 */
public class Lab5OutTests {
    private static final byte[] BYTES_SET_1 = {0, 2, 4, 6, 8, 3, 5, 7, 9, 32};

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BYTES_SET_1.length);
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream, BYTES_SET_1.length);


    @BeforeAll
    static void start() {
        System.out.println("Begin Tests");
    }

    @Test
    @DisplayName("Test write(int b)")
    void testWrite1() throws IOException {
        System.out.println("Tests writing a specified byte to a BufferedOutputStream ");

        for (byte number : BYTES_SET_1) {
            bufferedOutputStream.write(number);
        }
        bufferedOutputStream.flush();
        byte[] bytesWritten = byteArrayOutputStream.toByteArray();
        Assertions.assertArrayEquals(BYTES_SET_1, bytesWritten);

        //testing the IllegalStateException
        bufferedOutputStream.writeBit(0);
        bufferedOutputStream.writeBit(0);
        bufferedOutputStream.writeBit(0);

        Assertions.assertThrows(IllegalStateException.class, () -> bufferedOutputStream.write(0));
    }

    @Test
    @DisplayName("Test write(byte[] b)")
    void testWrite2() throws IOException {
        System.out.println("Tests writing an array of bytes to a BufferedOutputStream ");

        bufferedOutputStream.write(BYTES_SET_1);
        bufferedOutputStream.flush();

        byte[] bytesWritten = byteArrayOutputStream.toByteArray();
        Assertions.assertArrayEquals(BYTES_SET_1, bytesWritten);

        //testing the throwing of IllegalStateException
        bufferedOutputStream.writeBit(0);
        bufferedOutputStream.writeBit(0);
        bufferedOutputStream.writeBit(0);

        Assertions.assertThrows(IllegalStateException.class, () -> bufferedOutputStream.write(0));
    }

    @Test
    @DisplayName("Test writeBit(int bit)")
    void testWrite3() throws IOException {
        System.out.println("Tests writing a bit to a BufferedOutputStream ");

        bufferedOutputStream.writeBit(2);
        byte byteTest = (byte) 0b0000000000000010;
        for (int i = 0; i < 6; i++) {
            bufferedOutputStream.writeBit(2);
        }

        byte byteWritten = byteArrayOutputStream.toByteArray()[bufferedOutputStream.bitNumber];
        Assertions.assertEquals(byteTest, byteWritten);
    }

    @Test
    @DisplayName("Test flush")
    void testFlush() throws IOException {
        System.out.println("Tests flushing the bufferedOutputStream");

        for (byte number : BYTES_SET_1) {
            bufferedOutputStream.write(number);
        }
        bufferedOutputStream.flush();

        byte[] bytesWritten = byteArrayOutputStream.toByteArray();
        // testing that the outputStream is correctly written
        Assertions.assertArrayEquals(BYTES_SET_1, bytesWritten);
        // Testing if position becomes 0 after flushing
        Assertions.assertEquals(0, bufferedOutputStream.position);

        //testing the IllegalStateException
        bufferedOutputStream.writeBit(0);
        bufferedOutputStream.writeBit(0);
        bufferedOutputStream.writeBit(0);

        Assertions.assertThrows(IllegalStateException.class, () -> bufferedOutputStream.write(0));
    }

    @AfterAll
    static void stop() {
        System.out.println("Done with all test");
    }

}