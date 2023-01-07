/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Nothing
 * Name: malisad
 * Created 4/20/2021
 */
package malisad;


/**
 * Course: CS2852
 * Term Spring 2021-2022
 * MorseTree purpose:
 *
 * @author malisad
 * @version created on 4/20/2021 at 10:39 AM
 */
public class MorseTree<E> {

    Node root;

    /**
     *
     */
    public MorseTree() {
        root = new Node(null);
    }

    /**
     * @param symbol
     * @param code
     */
    public void add(E symbol, String code) {
        Node current = root;

        for (int i = 0; i < code.length(); i++) {

            if (code.charAt(i) == '.') {
                if (current.left == null) {
                    current.left = new Node(null);
                }
                current = current.left;

            } else if (code.charAt(i) == '-') {
                if (current.right == null) {
                    current.right = new Node(null);
                }
                current = current.right;
            }
        }
        current.value = symbol;
    }

    /**
     * @param code the code to be decoded
     * @throws IllegalArgumentException exception throw an
     * exception if an unacceptable character has been found
     */
    public E decode(String code) {
        if (code.contains(".") || code.contains("-") || code.contains(" ")) {
            return searchForElement(root, code);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param current current node
     * @param code being searched for
     * @return the value if found
     */
    private E searchForElement(Node current, String code) {
        if (current == null) {
            return null;
        }
        if (code.length() == 0) {
            return current.value;
        } else {
            if (code.charAt(0) == '.') {
                return searchForElement(current.left, code.substring(1));
            } else if (code.charAt(0) == '-') {
                return searchForElement(current.right, code.substring(1));
            }
        }
        return null;
    }

    class Node {
        E value;
        public Node left;
        public Node right;

        public Node(E value) {
            this.value = value;
            right = null;
            left = null;
        }
    }

}