/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class SmallerBigger
 * Name: malisad
 * Created 4/21/2021
 */
package malisad;


import java.util.List;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * SmallerBigger purpose:
 *
 * @author malisad
 * @version created on 4/21/2021 at 5:51 AM
 */
public class SmallerBiggerSort {


    private static int called = 0;

    /**
     * method implements an non-recursive sort
     *method puts the first element in its correct position
     *  making sure the elements before it are less that first
     * and the elements after it are greater than first
     *
     *  This method was inspired by the partition method from
     *  https://www.geeksforgeeks.org/quick-sort/
     * @param list  list to be sorted
     * @param start the starting position
     * @param end   the end position
     * @param <T>   generic variable
     * @return index of the first element once it has been sorted
     *
     */
    public static <T extends Comparable<T>> int smallerBigger(List<T> list, int start, int end) {
        T first = list.get(start);
        T placeHolder;
        int smallIdx = start - 1;
        int Alreadysorted = 0;

        for (int x = start; x <= end - 1; x++) {
            if (list.get(x).compareTo(first) < 0) {

                smallIdx++;
                placeHolder = list.get(smallIdx);
                list.set(smallIdx, list.get(x));
                list.set(x, placeHolder);
                Alreadysorted++;
            }
        }

        if (Alreadysorted > 0 || list.get(end).compareTo(first) < 0) {
            placeHolder = list.get(smallIdx + 1);
            list.set(smallIdx + 1, list.get(end));
            list.set(end, placeHolder);
        }

        if (list.indexOf(first) != 0) {
            int num = list.indexOf(first) - 1;
            while (list.get(num).compareTo(first) > 0) {
                placeHolder = list.get(num);
                list.set(num, first);
                list.set(list.indexOf(first) + 1, placeHolder);
                num = list.indexOf(first) - 1;
            }
        }

        if (called > 0) {
            if (list.get(end).compareTo(list.get(end - 1)) < 0) {
                placeHolder = list.get(end);
                list.set(end, list.get(end - 1));
                list.set(end - 1, placeHolder);
            }
        }

        return list.indexOf(first);
    }

    /**
     * sorts all the elements in the list from smallest
     * to largest
     *
     * @param list  list to be sorted
     * @param start the beginning of the list unless specified
     * @param end   the size of the list unless specified
     * @param <T> type
     */
    public static <T extends Comparable<T>> void sort(List<T> list, int start, int end) {
        called++;
        if (list != null && list.size() >= end && start + 1 < end) {

            int result = smallerBigger(list, start, end);

            sort(list, start, result - 1);
            sort(list, result + 1, end);

        }
    }

    /**
     * calls recursive sort
     * @param list list of elements passed in
     * @param <T> type
     */
    public static <T extends Comparable<T>> void sort(List<T> list) {
        sort(list, 0, list.size() - 1);
    }

}