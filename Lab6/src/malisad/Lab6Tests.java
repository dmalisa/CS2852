/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Lab6Tests
 * Name: malisad
 * Created 4/19/2021
 */
package malisad;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * Lab6Tests purpose: to test the smaller bigger sort
 *
 * @author malisad
 * @version created on 4/19/2021 at 8:11 PM
 */
public class Lab6Tests {


    private static final double NS_TO_MS = Math.pow(10.0, -6);

    public static void main(String[] args) {

        Lab6Tests tests = new Lab6Tests();
        System.out.println("The time it takes to sort a randomly sort list: " + tests.testSort());
        System.out.println("The time it takes to sort a partially sorted list: "+tests.testSort2());
        System.out.println("The time it takes to sort an already sorted list: "
                +tests.testSortSortedList());

    }

    @BeforeAll
    static void start() {
        System.out.println("Begin Tests");
    }

    @Test
    @DisplayName("Testing smaller bigger")
    void testSmallerBiggerSort() {
        System.out.println("Tests the smallerBigger method with list 1");

        List<Integer> list1 = new ArrayList<>();
        list1.add(8);
        list1.add(3);
        list1.add(8);
        list1.add(5);
        list1.add(12);
        list1.add(1);
        list1.add(8);
        list1.add(18);
        list1.add(13);

        System.out.println(list1);

        Assertions.assertEquals(3, SmallerBiggerSort.smallerBigger(list1, 0, list1.size() - 1));
        System.out.println(list1);
    }

    @Test
    @DisplayName("Testing smaller bigger")
    void testSmallerBiggerSort2() {
        System.out.println("Tests the smallerBigger method with list 2");

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);

        System.out.println(list2);

        Assertions.assertEquals(0, SmallerBiggerSort.smallerBigger(list2, 0, list2.size() - 1));
        System.out.println(list2);
    }

    @Test
    @DisplayName("Testing smaller bigger")
    void testSmallerBiggerSort3() {
        System.out.println("Tests the smallerBigger method with list 3");
        List<Integer> list3 = new ArrayList<>();
        list3.add(8);
        list3.add(3);
        list3.add(8);
        list3.add(5);
        list3.add(12);
        list3.add(1);
        list3.add(2);
        list3.add(11);
        list3.add(3);
        list3.add(2);
        list3.add(8);
        list3.add(18);
        list3.add(13);

        System.out.println(list3);

        Assertions.assertEquals(6, SmallerBiggerSort.smallerBigger(list3, 0, list3.size() - 1));
        System.out.println("the new list is:");
        System.out.println(list3);
    }

    @Test
    @DisplayName("testing a random list")
    void testSmallerBiggerSortRandom() {
        List<Integer> list = new ArrayList<>();

        for (int v = 0; v < 11; v++) {
            list.add((int) (Math.random() * 20));
        }

        System.out.println(list);
        System.out.println(SmallerBiggerSort.smallerBigger(list, 0, list.size() - 1));
        System.out.println(list);

    }


    @Test
    @DisplayName("Recursive Sort")
    void testSortRandom() {
        ArrayList<Integer> list = new ArrayList<>();


        for (int v = 0; v < 11; v++) {
            list.add((int) (Math.random() * 20));
        }


        System.out.println(list);
        SmallerBiggerSort.sort(list);
        System.out.println(list);
    }


    @Test
    @DisplayName("Recursive Sort")
    double testSort() {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(8);
        list.add(3);
        list.add(8);
        list.add(5);
        list.add(12);
        list.add(1);
        list.add(2);
        list.add(11);
        list.add(3);
        list.add(2);
        list.add(8);
        list.add(18);
        list.add(13);

        System.out.println(list);
        final long startTime = System.nanoTime();
        SmallerBiggerSort.sort(list);
        final long endTime = System.nanoTime();
        final long elapsed = endTime - startTime;
        System.out.println(list);

        return elapsed*NS_TO_MS;
    }


    @Test
    @DisplayName("Recursive Sort")
    double testSort2() {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(50);
        list.add(70);
        list.add(90);
        list.add(40);
        list.add(10);
        list.add(30);
        list.add(100);


        System.out.println(list);
        final long startTime = System.nanoTime();
        SmallerBiggerSort.sort(list);
        final long endTime = System.nanoTime();
        final long elapsed = endTime - startTime;
        System.out.println(list);

        return elapsed*NS_TO_MS;
    }


    @Test
    @DisplayName("Recursive Sort")
    double testSortSortedList() {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);


        System.out.println(list);
        final long startTime = System.nanoTime();
        SmallerBiggerSort.sort(list);
        final long endTime = System.nanoTime();
        final long elapsed = endTime - startTime;
        System.out.println(list);
        return elapsed*NS_TO_MS;
    }

}
