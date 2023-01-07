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
import java.io.InputStream;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * BufferedInputStream purpose: Implement the BufferedInputStream
 *
 * @author malisad
 * @version created on 4/6/2021 at 10:27 AM
 */
public class BufferedInputStream implements AutoCloseable, Closeable {

    InputStream inputStream;
    byte[] bufferedInput;
    int size;
    int position = 0;
    int bytesRead = 0;
    int bitNumber = -1;
    int timesCalled = 0;
    int readBitCalled = 0;
    byte byteRead;

    /**
     * constructor creates an internal array
     * to store the buffered input
     *
     * @param in BufferedInputStream object
     */
    BufferedInputStream(InputStream in) {
        final byte DEFAULT_BUFFER_SIZE = 32;
        this.size = DEFAULT_BUFFER_SIZE;
        inputStream = in;
        bufferedInput = new byte[DEFAULT_BUFFER_SIZE];
    }

    /**
     * constructor creates an internal array
     *
     * @param in   BufferedInputStream object
     * @param size the size of the bufferedInput array
     */
    BufferedInputStream(InputStream in, int size) {
        this.size = size;
        inputStream = in;
        if (size < 0) {
            throw new IllegalArgumentException();
        }
        bufferedInput = new byte[size];
    }

    /**
     * Reads the next bit of data from the input stream.
     *
     * @return bit value as an int or -1 if stream end has
     * been reached
     * @throws IOException if one occurs;
     */
    public int readBit() throws IOException {
        readBitCalled = readBitCalled + 1;
        if (bitNumber == -1 || bitNumber == 8) {
            int result = inputStream.read();
            if (result == -1) {
                return -1;
            }
            byteRead = (byte) result;
            bitNumber = 8;
        }
        bitNumber = bitNumber--;
        int bit = (byteRead >> bitNumber) & 0b00000001;

        return bit;
    }

    /**
     * Reads the next byte of data from the input stream
     *
     * @return -1 if the end of the stream is reached , the next byte of data
     * @throws IOException if encountered
     */
    public int read() throws IOException {
        int returnVal;
        if (readBitCalled > 0 && readBitCalled % 8 != 0) {
            throw new IllegalStateException();
        }
        if (position == bytesRead) {
            bytesRead = inputStream.read(bufferedInput);
            timesCalled++;
            position = 0;
            if (bytesRead == 0 || bytesRead == -1) {
                return -1;
            }
        }

        returnVal = bufferedInput[position++];
        return returnVal;

    }

    /**
     * Reads a number of bytes from the input stream and stores them into the buffer
     *
     * @param b array being passed
     * @return the total number of bytes read into the buffer,
     * or -1 if there is no more data because the
     * end of the stream has been reached.
     * @throws IOException if encountered
     */
    public int read(byte[] b) throws IOException {
        int numBytesRead = 0;
        if (readBitCalled > 0 && readBitCalled % 8 != 0) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < b.length; i++) {
            int result = read();
            if (result == -1) {
                return numBytesRead;
            }
            numBytesRead++;
            b[i] = (byte) result;
        }

        return numBytesRead;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}