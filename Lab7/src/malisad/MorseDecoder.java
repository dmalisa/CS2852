/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Nothing
 * Name: malisad
 * Created 4/20/2021
 */
package malisad;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * MorseDecoder purpose:
 *
 * @author malisad
 * @version created on 4/20/2021 at 10:39 AM
 */
public class MorseDecoder {

    MorseTree<String> morseTree = new MorseTree<>();
    private static String filename;

    /**
     * @param path
     * @throws  IOException  if encountered
     */
    public void readMorseCode(Path path) throws IOException {
        String code;
        String result = "";
        Scanner input = null;
        FileWriter writer = new FileWriter(filename);
        try {
            input = new Scanner(path);
            String[] characters;

            while (input.hasNext()) {
                code = input.next();
                characters = code.split(" ");

                if (!(code.equals("*"))) {
                    for (String s : characters) {
                        if (code.equals(".-.-")) {
                            result += "\n";
                        } else {
                            result += morseTree.decode(s);
                        }
                    }
                } else {
                    System.out.println("Skipping: *");
                }
            }
            writer.write(result);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("I/O error has occurred");
        } catch (IllegalArgumentException e) {
            System.out.println("A character was entered that is not included in the morse table ");
        } finally {
            if (input != null) {
                input.close();
                writer.close();
            }
        }
    }


    /**
     * @param path of file to be read
     */
    public void loadDecoder(Path path) {
        String symbol;
        String code;
        String lettersAndMorse;
        Scanner in = null;

        try {
            in = new Scanner(path);

            while (in.hasNextLine()) {
                lettersAndMorse = in.nextLine();
                if (lettersAndMorse.contains("\\")) {
                    symbol = lettersAndMorse.substring(0, 2);
                    code = lettersAndMorse.substring(2);
                } else {
                    symbol = String.valueOf(lettersAndMorse.charAt(0));
                    code = lettersAndMorse.substring(1);
                }
                morseTree.add(symbol, code);
            }

        } catch (IOException e) {
            System.out.println("I/O error has occurred");
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static void main(String[] args) {
        MorseDecoder morseDecoder = new MorseDecoder();

        File infile = new File("data/morsecode.txt");
        morseDecoder.loadDecoder(infile.toPath());

        Scanner input = new Scanner(System.in);
        System.out.println("Enter an input filename:");
        String name = input.nextLine();
        File file = new File(name);
        System.out.println("Enter an output filename");
        filename = input.nextLine();
        try {
            morseDecoder.readMorseCode(file.toPath());
        } catch (IOException e) {
            System.out.println("Error with entered file name");
        }

    }
}