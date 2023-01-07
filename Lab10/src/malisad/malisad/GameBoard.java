/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class GameBoard
 * Name: malisad
 * Created 5/7/2021
 */
package malisad.malisad;


import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * GameBoard purpose:
 *
 * @author malisad
 * @version created on 5/7/2021 at 11:25 AM
 */
public class GameBoard {


    private final Collection<String> dictionary;
    Cell[][] grid;
    private int colLength = 0;
    private int rowLength = 0;
    int numWordsFound = 0;
    float secondsTaken;

    public GameBoard(Collection<String> emptyDictionary) {
        dictionary = emptyDictionary;
    }

    /**
     * @param path
     * @throws IOException
     */
    public void loadDictionary(Path path) throws IOException {
        Scanner inFile = new Scanner(path);
        String word;

        while (inFile.hasNextLine()) {
            word = inFile.nextLine();
            dictionary.add(word);
        }

    }

    /**
     * @param path
     */
    public void loadGrid(Path path) {
        ArrayList<Character> letter = new ArrayList<>();

        try {
            Scanner inFile = new Scanner(path);
            int count = 0;

            while (inFile.hasNextLine()) {
                count++;
                String line = inFile.nextLine();
                ArrayList<Character> letters = new ArrayList<>();
                for (int x = 0; x < line.length(); x++) {
                    letters.add(line.charAt(x));
                    letter.add(line.charAt(x));
                }
                colLength = letters.size();
            }
            rowLength = count;

        } catch (IOException e) {
            System.out.println("I/O error has occurred");
        }

        grid = new Cell[rowLength][colLength];
        int count = 0;


        for (int r = 0; r < rowLength; r++) {
            for (int c = 0; c < colLength; c++) {
                Cell cell = new Cell(r, c, letter.get(count));
                grid[r][c] = cell;
                count++;
            }
        }

    }


    private Collection<String> recursiveSearch(int row, int col, String partialWord, HashSet<Cell> visited, boolean isFourWay) {
        ArrayList<String> wordsFound = new ArrayList<>();
        String word;


        if (row < rowLength && col < colLength &&
                partialWord.length() <= 10 && row >= 0 && col >= 0) {


            if (!(visited.contains(grid[row][col]))) {

                visited.add(grid[row][col]);
                word = grid[row][col].getLetter() + "";

                partialWord += word.toLowerCase();

                if (dictionary.contains(partialWord) && partialWord.length() >= 3) {
                    if (!(wordsFound.contains(partialWord))) {
                        wordsFound.add(partialWord + "\n");
                        numWordsFound++;
                    }
                }

                wordsFound.addAll(recursiveSearch(row + 1, col, partialWord, visited, isFourWay));
                wordsFound.addAll(recursiveSearch(row - 1, col, partialWord, visited, isFourWay));
                wordsFound.addAll(recursiveSearch(row, col + 1, partialWord, visited, isFourWay));
                wordsFound.addAll(recursiveSearch(row, col - 1, partialWord, visited, isFourWay));

                if (!isFourWay) {
                    wordsFound.addAll(recursiveSearch(row + 1, col + 1, partialWord, visited, isFourWay));
                    wordsFound.addAll(recursiveSearch(row - 1, col - 1, partialWord, visited, isFourWay));
                    wordsFound.addAll(recursiveSearch(row + 1, col - 1, partialWord, visited, isFourWay));
                    wordsFound.addAll(recursiveSearch(row - 1, col + 1, partialWord, visited, isFourWay));
                }
                visited.remove(grid[row][col]);
            }

        }

        return wordsFound;
    }


    public Collection<String> findWords(boolean isFourWay) {
        Collection<String> wordsFound = new ArrayList<>();
        Collection<String> word;

        long startTime = System.currentTimeMillis();
        for (int x = 0; x < rowLength; x++) {
            for (int y = 0; y < colLength; y++) {
                word = recursiveSearch(x, y, "", new HashSet<>(), isFourWay);
                wordsFound.addAll(word);
            }
        }

        long endTime = System.currentTimeMillis();
        secondsTaken = (endTime - startTime) / 1000F;
        return wordsFound;
    }


    /**
     * Course: CS2852
     * Term Spring 2021-2022
     * Cell purpose:
     *
     * @author malisad
     * @version created on 5/8/2021 at 6:37 PM
     */
    public class Cell {

        private int row;
        private int col;
        char letter;

        public Cell(int row, int col, char letter) {
            this.row = row;
            this.col = col;
            this.letter = letter;
        }

        /**
         * calculates and returns the hashcode of a cell
         *
         * @return hashcode of cell passed in
         */
        public int hashCode() {
            return Objects.hash(this.row, this.col, this.letter);
        }

        @Override
        public boolean equals(Object o) {
            Cell temp = (Cell) o;
            return temp.col == this.col && temp.row == this.row
                    && temp.letter == this.letter;
        }

        public char getLetter() {
            return this.letter;
        }


    }
}