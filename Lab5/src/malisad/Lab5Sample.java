/*
 * Course: CS2852 - All
 * Spring 2019
 * Lab 5 - Sample Buffered IO program
 * Name: Dr. Taylor
 * Created: 03/24/2019
 */
package malisad;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Simple program that demonstrates how to read/write from
 * Buffered Input/Output Streams.
 */
public class Lab5Sample {
    /**
     * The byte array to be written and read
     */
    private final static byte[] BYTES = {0, 2, 4, 8, 16, 32, 64, (byte)128};

    public static void main(String[] ignored) {
        try {
            writeBytesToByteArrayOutputStream();
            readBytesFromByteArrayInputStream();
        } catch(IOException e) {
            System.out.println("Somehow an IOException was thrown.");
        }
    }

    /**
     * Reads bytes from a BufferedInputStream.
     * @throws IOException if an IOException is encountered
     */
    private static void readBytesFromByteArrayInputStream() throws IOException {
        try(ByteArrayInputStream bin = new ByteArrayInputStream(BYTES);
            BufferedInputStream in = new BufferedInputStream(bin)) {
            // Read bytes from input stream
            byte[] bytesRead = new byte[BYTES.length];
            for(int i=0; i<BYTES.length; i++) {
                int number = in.read();
                if(number==-1) {
                    System.out.println("Error: read too many bytes");
                }
                bytesRead[i] = (byte)number;
            }
            // Confirm that bytes read match what was in the input stream
            if(!Arrays.equals(bytesRead, BYTES)) {
                System.out.println("Error: bytes read don't match");
            }
        }
    }

    /**
     * Writes bytes to a BufferedOutputStream.
     * @throws IOException if an IOException is encountered
     */
    private static void writeBytesToByteArrayOutputStream() throws IOException {
        try(ByteArrayOutputStream bout = new ByteArrayOutputStream(BYTES.length);
            BufferedOutputStream out = new BufferedOutputStream(bout)) {
            // Write bytes to output stream
            for(byte number : BYTES) {
                out.write(number);
            }
            out.flush();
            // Get bytes out of the output stream byte array
            byte[] bytesWritten = bout.toByteArray();

            // Confirm that bytes in the output stream match what was written
            if(!Arrays.equals(bytesWritten, BYTES)) {
                System.out.println("Error: bytes written don't match");
            }
        }
    }
}
