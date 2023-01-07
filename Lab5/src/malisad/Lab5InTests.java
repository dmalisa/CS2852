/*
 *
 */

package malisad;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Tests the BufferedInputStream class for CS2852 Lab 5 assignment.
 */
public class Lab5InTests {

    private final static int BUFFER_SIZE = 8;
    private final static int BUFFER_SIZE_A = 11;
    private final static int BUFFER_SIZE_B = 16;
    private final static int MINOR_ERROR = -1;
    private final static String NULL_POINTER =
            ": throws a `NullPointerException` exception but shouldn't.";
    private final static String ARRAY_INDEX_OUT_OF_BOUNDS =
            ": throws an `ArrayIndexOutOfBoundsException` exception but shouldn't.";
    private final static String STACK_OVERFLOW =
            ": throws an `StackOverflowError` error but shouldn't.";
    private final static String IO_EXCEPTION =
            ": throws an `IOException` exception but shouldn't.";
    private final static String FILENAME = "text.txt";

    private static String filename;
    private static int numberFailed = 0;

    /**
     * Tests the BufferedInputStream class specified in CS2852 lab 5. The tests
     * produce an output file containing test results in a format that can be
     * inserted into grading file format used by Dr. Taylor.
     * @param ignored Not used
     */
    public static void main(String[] ignored) {
        filename = getOutputFilename(new BufferedInputStream(new ByteArrayInputStream(new byte[0])),
                "L5");
        try {
            writeTestIntro();

            testReadNth(MINOR_ERROR, BUFFER_SIZE, 0, createByteArray(1));
            testReadNth(MINOR_ERROR, BUFFER_SIZE, 0, createByteArray(2));
            testReadNth(MINOR_ERROR, BUFFER_SIZE, 7, createByteArray(8));
            testReadNth(MINOR_ERROR, BUFFER_SIZE, 8, createByteArray(12));
            testReadNth(MINOR_ERROR, BUFFER_SIZE_A, BUFFER_SIZE_A-1, createByteArray(16));
            testReadNth(MINOR_ERROR, BUFFER_SIZE_A, BUFFER_SIZE_A, createByteArray(16));
            testReadNth(MINOR_ERROR, BUFFER_SIZE_A, BUFFER_SIZE_A+1, createByteArray(16));
            testReadNth(MINOR_ERROR, BUFFER_SIZE_B, 5, createByteArray(24));
            testReadNth(MINOR_ERROR, BUFFER_SIZE_B, 23, createByteArray(24));
            testReadArray(MINOR_ERROR, "Read array same size as buffer",
                    BUFFER_SIZE, 0, new byte[8], createByteArray(8));
            testReadArray(MINOR_ERROR, "Read array smaller than buffer",
                    BUFFER_SIZE, 0, new byte[4], createByteArray(8));
            testReadArray(MINOR_ERROR, "Read array larger than buffer",
                    BUFFER_SIZE, 0, new byte[10], createByteArray(10));
            testReadArray(MINOR_ERROR, "Read array after partial read from the buffer",
                    BUFFER_SIZE, 2, new byte[6], createByteArray(8));
            testReadArray(MINOR_ERROR, "Read larger array after partial read from the buffer",
                    BUFFER_SIZE, 2, new byte[8], createByteArray(20));
            testReadArray(MINOR_ERROR, "Read smaller array after partial read from the buffer",
                    BUFFER_SIZE, 6, new byte[1], createByteArray(20));
            testReadEmptyFile();
            testReadNthBit(MINOR_ERROR, BUFFER_SIZE, 0);
            testReadNthBit(MINOR_ERROR, BUFFER_SIZE, 3);
            testReadNthBit(MINOR_ERROR, BUFFER_SIZE, 7);
            testReadNthBit(MINOR_ERROR, BUFFER_SIZE, BUFFER_SIZE);
            testReadNthBit(MINOR_ERROR, BUFFER_SIZE, 9);
            testIllegalStateOnParitalByteRead(MINOR_ERROR, BUFFER_SIZE, 0);
            testIllegalStateOnParitalByteRead(MINOR_ERROR, BUFFER_SIZE, 3);
            testIllegalStateOnParitalByteRead(MINOR_ERROR, BUFFER_SIZE, 7);
            testIllegalStateOnParitalByteRead(MINOR_ERROR, BUFFER_SIZE, 8);
            testIllegalStateOnParitalByteRead(MINOR_ERROR, BUFFER_SIZE, 15);

            writeTestOutro();
        } catch(IOException e) {
            System.err.println("Error writing file containing tests results.");
        }
        System.out.println("All tests completed.  Number failed: " + numberFailed);
    }


    /**
     * Tests reading a specified byte from a BufferedInputStream
     * @param deduction Number of points to deduct if test fails
     * @param bufferSize Size of the BufferedInputStream created
     * @param n index of the byte to be read
     * @param data array of bytes used to populate the BufferedInputStream
     */
    private static void testReadNth(int deduction, int bufferSize, int n, byte[] data) throws IOException {
        String description = "Reading " + stringify(n) + " byte from a buffer of size " +
                bufferSize;
        String details = description;
        boolean passed;
        try {
            BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data),
                    bufferSize);
            int result = in.read();
            for(int i=0; i<n; i++) {
                result = in.read();
            }
            details = "For a buffer size of " + bufferSize +
                    ", your implementation fails to read the " + stringify(n) +
                    " byte from an input stream with " + data.length +
                    " bytes in it. Expected: " + data[n] + " Actual: " + result;
            passed = data[n]==(byte)result;
        } catch(NullPointerException e) {
            passed = false;
            description += NULL_POINTER;
            details = description;
        } catch(ArrayIndexOutOfBoundsException e) {
            passed = false;
            description += ARRAY_INDEX_OUT_OF_BOUNDS;
            details = description;
        } catch(StackOverflowError e) {
            passed = false;
            description += STACK_OVERFLOW;
            details = description;
        } catch(IOException e) {
            passed = false;
            description += IO_EXCEPTION;
            details = description;
        }
        if(!passed) {
            numberFailed++;
            writeFail(description, details, deduction);
        }
    }

    /**
     * Tests reading a byte array
     * @param deduction Number of points to deduct if test fails
     * @param description Description of the test to be run
     * @param bufferSize Size of the BufferedInputStream created
     * @param preRead Number of bytes to read out of the BufferedInputStream prior to doing an
     *               array read
     * @param destination array to store the bytes read from the BufferedInputStream
     * @param data array of bytes used to populate the BufferedInputStream
     */
    private static void testReadArray(int deduction, String description, int bufferSize,
                                      int preRead, byte[] destination, byte[] data)
            throws IOException {
        String details = description;
        boolean passed = true;
        try {
            byte[] jdestination = new byte[destination.length];
            BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data),
                    bufferSize);
            java.io.BufferedInputStream jin = new java.io.BufferedInputStream(
                    new ByteArrayInputStream(data), bufferSize);
            while(preRead>0) {
                in.read();
                jin.read();
                --preRead;
            }
            int actual = in.read(destination);
            int expected = jin.read(jdestination);
            if(actual!=expected) {
                passed = false;
                details = "Your implementation fails to read the correct number of bytes."
                        + " Expected: " + expected + " Actual: " + actual;
            }
            if(passed && !Arrays.equals(destination, jdestination)) {
                passed = false;
                details = "Your implementation fails to read the correct bytes into the array."
                        + " Expected: `" + Arrays.toString(jdestination)
                        + "` Actual: `" + Arrays.toString(destination) +"`";
            }
        } catch(NullPointerException e) {
            passed = false;
            description += NULL_POINTER;
            details = description;
        } catch(ArrayIndexOutOfBoundsException e) {
            passed = false;
            description += ARRAY_INDEX_OUT_OF_BOUNDS;
            details = description;
        } catch(StackOverflowError e) {
            passed = false;
            description += STACK_OVERFLOW;
            details = description;
        } catch(IOException e) {
            passed = false;
            description += IO_EXCEPTION;
            details = description;
        }
        if(!passed) {
            numberFailed++;
            writeFail(description, details, deduction);
        }
    }

    /**
     * Tests if the BufferedInputStream's read method returns -1 for an empty file
     */
    public static void testReadEmptyFile() throws IOException {
        String description = "Reading from an empty file";
        String details = description + " should return -1.";
        delete(FILENAME);
        try (OutputStream out = Files.newOutputStream(Paths.get(FILENAME))) {
            out.write(new byte[0]);
        }
        boolean passed = true;
        try (InputStream in = Files.newInputStream(Paths.get(FILENAME))) {
            BufferedInputStream bIn = new BufferedInputStream(in);
            if (bIn.read() != -1) {
                passed = false;
            }
        } catch(NullPointerException e) {
            passed = false;
            description += NULL_POINTER;
            details = description;
        } catch(ArrayIndexOutOfBoundsException e) {
            passed = false;
            description += ARRAY_INDEX_OUT_OF_BOUNDS;
            details = description;
        } catch(StackOverflowError e) {
            passed = false;
            description += STACK_OVERFLOW;
            details = description;
        } catch(IOException e) {
            passed = false;
            description += IO_EXCEPTION;
            details = description;
        }
        if(!passed) {
            numberFailed++;
            writeFail(description, details, -1);
        }
    }

    /**
     * Tests reading a specified bit from a BufferedInputStream
     * @param deduction Number of points to deduct if test fails
     * @param bufferSize Size of the BufferedInputStream created
     * @param n index of the bit to be read
     */
    private static void testReadNthBit(int deduction, int bufferSize, int n) throws IOException {
        String description = "Reading " + stringify(n) + " bit";
        String details = description;
        boolean passed;
        try {
            byte[] data = {0, 0};
            if(n<8) {
                data[0] = (byte)(1 << (7-n));
            } else {
                data[1] = (byte) (1 << (7 - (n-8)));
            }
            BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data),
                    bufferSize);
            int result = in.readBit();
            for(int i=0; i<n; i++) {
                result = in.readBit();
            }
            details = "Your implementation fails to read the " + stringify(n) +
                    " bit from an input stream.";
            passed = 1==(byte)result;
        } catch(NullPointerException e) {
            passed = false;
            description += NULL_POINTER;
            details = description;
        } catch(ArrayIndexOutOfBoundsException e) {
            passed = false;
            description += ARRAY_INDEX_OUT_OF_BOUNDS;
            details = description;
        } catch(IOException e) {
            passed = false;
            description += IO_EXCEPTION;
            details = description;
        }
        if(!passed) {
            numberFailed++;
            writeFail(description, details, deduction);
        }
    }

    /**
     * Tests to ensure that read() methods throw IllegalStateException if and only if a partial
     * byte has been read.
     * @param deduction Number of points to deduct if test fails
     * @param bufferSize Size of the BufferedInputStream created
     * @param n index of the bit to be read
     */
    private static void testIllegalStateOnParitalByteRead(int deduction, int bufferSize, int n)
            throws IOException {
        boolean shouldThrow = ((n+1)%8)!=0;
        String description = "Reading a byte immediately after reading the " + stringify(n)
                + " bit should " + (shouldThrow ? "" : "not ") + "throw an IllegalStateException.";
        String details = description;
        boolean passed = true;
        try {
            byte[] data = {0, 0, 0, 0};
            BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data),
                    bufferSize);
            for(int i=0; i<=n; i++) {
                in.readBit();
            }
            in.read();
            in.read(data);
            if(shouldThrow) {
                passed = false;
            }
        } catch(NullPointerException e) {
            passed = false;
            description += NULL_POINTER;
            details = description;
        } catch(ArrayIndexOutOfBoundsException e) {
            passed = false;
            description += ARRAY_INDEX_OUT_OF_BOUNDS;
            details = description;
        } catch(IllegalStateException e) {
            if(!shouldThrow) {
                passed = false;
            }
        } catch(IOException e) {
            passed = false;
            description += IO_EXCEPTION;
            details = description;
        }
        if(!passed) {
            numberFailed++;
            writeFail(description, details, deduction);
        }
    }

    /**
     * Get the ordinal abbreviation for the specified integer
     * @param n the number of interest
     * @return The string representation of n in abreviated ordinal form
     */
    private static String stringify(int n) {
        switch (n) {
            case 0:
                return "1st";
            case 1:
                return "2nd";
            case 2:
                return "3rd";
        }
        return (n+1) + "th";
    }

    /**
     * Write a message for a failed test to the file using Dr. Taylor's grading markup.
     * @param description the description of the failure
     * @param details the details associated with the failure
     * @param deduction the number of points to deduct
     */
    private static void writeFail(String description, String details, int deduction)
            throws IOException {
        String message = "\n!!! MINUS: " + deduction + " &#x1F61E Test failed: " + description +
                "\n    " + details + "\n";
        System.out.println(message);
        Files.write(Paths.get(filename), message.getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * Creates an array of bytes
     * @param size the length of the byte array to be created
     * @return the array of bytes
     */
    private static byte[] createByteArray(int size) {
        byte[] buf = new byte[size];
        for(int i=0; i<buf.length; i++) {
            buf[i] = (byte)i;
        }
        return buf;
    }

    /**
     * Surrounds the String with quotes if it doesn't look like
     * it holds an integer or null.
     * @param check String to which formatting is applied
     * @return the formatted string
     */
    private static String surroundWithQuotes(String check) {
        if(check!=null && !check.matches(".*\\d+.*")) {
            check = '"' + check + '"';
        }
        return check;
    }

    /**
     * Construct an output filename based on the package name of object and
     * the lab.
     * @param obj Object from which to extract the package name
     * @param lab Lab assignment identifier, e.g., "L5" for the week 5 lab assignment
     * @return Desired output filename
     */
    private static String getOutputFilename(Object obj, String lab) {
        String cls = obj.getClass().toString();
        return "..\\" + "\\2852" + cls.substring(cls.indexOf(' ')+1, cls.lastIndexOf('.')) +
                lab + ".txt";
    }

    /**
     * Creates a new file and writes a section header
     * @throws IOException Thrown if trouble is encountered writing to the output file
     */
    private static void writeTestIntro() throws IOException {
        String message = "\n# Results of Instructor Testing\n";
        Files.write(Paths.get(filename), message.getBytes());
    }

    /**
     * Writes an "All Tests Passed" message if appropriate
     * @throws IOException Thrown if trouble is encountered writing to the output file
     */
    private static void writeTestOutro() throws IOException {
        if(numberFailed==0) {
            String message = "\n!!! BONUS: All Tests Passed!\n";
            Files.write(Paths.get(filename), message.getBytes(), StandardOpenOption.APPEND);
        }
    }


    /**
      * Deletes the specified file, if it exists
      * @param filename name of file to be deleted
      */
    private static void delete(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            System.out.println("IO Exception when deleting file");
        }
    }

}
