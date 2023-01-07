/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class WordSearchCLI
 * Name: malisad
 * Created 5/10/2021
 */
package malisad;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * WordSearchCLI purpose:
 *
 * @author malisad
 * @version created on 5/10/2021 at 4:57 PM
 */
public class WordSearchCLI {

    private static String searchMethod;
    private static String gridFileName;
    private static String wordListName;
    private static String dictionaryStructure;
    private static Collection<String> emptyDictionary;

    public static void main(String[] args) {

        GameBoard gameBoard = new GameBoard(emptyDictionary);

//        System.out.println("Choose a search technique, 1: 4way " +
//                "\n                           2: 8way ");
        searchMethod = args[0];

        boolean isFourWay;
        isFourWay = searchMethod.equals("1");

//        System.out.println("Enter grid file name");
        gridFileName = args[1];

//        System.out.println("Name of file with list of words");
        wordListName = args[2];

//        System.out.println("Choose a data structure for your dictionary");
//        System.out.println("enter 1: ArrayList" + " \nenter 2: LinkedList" +
//                "\nenter 3: HashSet" + "\nenter 4: TreeSet");
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

        File dictionaryFileName = new File("\\data\\"+wordListName);
        File gridFile = new File("\\data\\"+gridFileName);

        try {
            gameBoard.loadDictionary(dictionaryFileName.toPath());
            gameBoard.loadGrid(gridFile.toPath());
            gameBoard.findWords(isFourWay);
        } catch (IOException e) {
            System.out.println("IOException has occurred");
        }

    }

//    public static String getStructure() {
//        return dictionaryStructure;
//    }
}
