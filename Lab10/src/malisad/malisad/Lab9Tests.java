/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Lab9Tests
 * Name: malisad
 * Created 5/10/2021
 */
package malisad.malisad;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * Lab9Tests purpose:
 *
 * @author malisad
 * @version created on 5/10/2021 at 6:25 PM
 */
public class Lab9Tests {
    private static String searchMethod;
    private static String gridFileName;
    private static String wordListName;
    private static String dictionaryStructure;
    private static Collection<String> emptyDictionary;


    public static void main(String[] args) {

//        Scanner in = new Scanner(System.in);

        System.out.println(" 4way " +
                "\n   8way ");
//        searchMethod = "2";
        searchMethod = args[0];

        boolean isFourWay;
        isFourWay = searchMethod.equals("1");

        System.out.println("Enter grid file name");
//        gridFileName = "grid6x6";
        gridFileName = args[1];

        System.out.println("Name of file with list of words");
//        wordListName = "2000words";
        wordListName = args[2];

        System.out.println("Choose a data structure for your dictionary");
        System.out.println("enter 1: ArrayList" + " \nenter 2: LinkedList" +
                "\nenter 3: HashSet" + "\nenter 4: TreeSet");
//        dictionaryStructure = "3";
        dictionaryStructure = args[3];

        switch (dictionaryStructure) {
            case "1":
                emptyDictionary = new ArrayList<>();
                break;
            case "2":
                emptyDictionary = new LinkedList<>();
                break;
            case "3":
                emptyDictionary = new HashSet<>();
                break;
            case "4":
                emptyDictionary = new TreeSet<>();
                break;
        }

        GameBoard gameBoard = new GameBoard(emptyDictionary);

        File dictionaryFileName = new File("data/" + wordListName + ".txt");
        File gridFile = new File("data/" + gridFileName + ".txt");

        try {
            gameBoard.loadDictionary(dictionaryFileName.toPath());
            gameBoard.loadGrid(gridFile.toPath());
            System.out.println(gameBoard.findWords(isFourWay));
            System.out.println("\n" + gameBoard.numWordsFound);
            System.out.println("\nTime taken to search: " + gameBoard.secondsTaken + "seconds");
        } catch (IOException e) {
            System.out.println("IOException has occurred");
        }

    }

}



