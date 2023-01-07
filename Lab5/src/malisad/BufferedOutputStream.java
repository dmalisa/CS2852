/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Example
 * Name: malisad
 * Created 4/6/2021
 */
package malisad;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * BufferedOutputStream purpose:
 *
 * @author malisad
 * @version created on 4/6/2021 at 10:27 AM
 */
public class BufferedOutputStream implements Closeable, AutoCloseable {

    OutputStream outputStream;
    byte[] bufferedOutput;
    int size;
    int position = 0;
    int bitNumber = 8;
    int writeBitCalled;

    /**
     * Creates a BufferedOutputStream object
     * An internal array is also created to store the buffered output.
     * The array must be the length of the default buffer size.
     *
     * @param out output stream
     */
    BufferedOutputStream(OutputStream out) {
        final byte DEFAULT_BUFFER_SIZE = 32;
        this.size = DEFAULT_BUFFER_SIZE;
        outputStream = out;
        bufferedOutput = new byte[DEFAULT_BUFFER_SIZE];
    }

    /**
     * Creates a BufferedOutputStream object
     * An internal array is also created to store the buffered output.
     *  The array must be the length of the buffer size.
     * @param out output stream
     * @param size of the output stream
     */
    BufferedOutputStream(OutputStream out, int size) {
        this.size = size;
        outputStream = out;
        if (size < 0) {
            throw new IllegalArgumentException();
        }
        bufferedOutput = new byte[size];
    }

    public void writeBit(int bit) throws IOException {
        writeBitCalled = writeBitCalled +1;
        byte partialByte = (byte) bit;
        bitNumber = bitNumber--;
        if (bitNumber == 7) {
            partialByte = (byte) (bit << 7);
        } else if (bitNumber > 0) {
            partialByte = (byte) ((bit << bitNumber) | partialByte);
        } else if (bitNumber == 0) {
            partialByte = (byte) (bit | partialByte);
            this.write(partialByte);
        }
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param b int to be witten to the output stream
     * @throws IOException if encountered is thrown
     */
    public void write(int b) throws IOException {
        if (writeBitCalled > 0 && writeBitCalled% 8 != 0) {
            throw new IllegalStateException();
        }
        if (position >= size) {
            flush();
        }
        bufferedOutput[position] = (byte) b;
        position++;
    }

    /**
     * Writes b.length bytes from the specified byte array to this output stream.
     *
     * @param b array of bytes
     * @throws IOException if encountered
     */
    public void write(byte[] b) throws IOException {
        if (writeBitCalled > 0 && writeBitCalled % 8 != 0) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < b.length; i++) {
            this.write(b[i]);
        }
    }

    /**
     * Flushes this output stream and forces any buffered
     * output bytes to be written out
     *
     * @throws IOException if encountered
     */
    public void flush() throws IOException {
        if (writeBitCalled > 0 && writeBitCalled % 8 != 0) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < bufferedOutput.length; i++) {
            outputStream.write(bufferedOutput[i]);
        }
        position = 0;
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

}