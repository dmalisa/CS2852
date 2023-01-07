/*
 * FIX
 */
package malisad;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Driver program for CS2852 lab 1 that runs various benchmarking
 * experiments to compare the performance of the ArrayList and LinkedList
 * data structures.
 *
 * @author nowling and taylor
 * @version 2021.3.4
 */
public class BenchmarkDriver {

    private static final Random RANDOM = new Random();
    private static final double NS_TO_MS = Math.pow(10.0, -6);

    /**
     * Calculates the elapsed time to add the given number of items
     * to the waiting list.  The list is cleared before items are added.
     *
     * @param list   determines the specific type of list to be benchmarked
     * @param nItems number of items to be added to the list
     * @return Elapsed time in milliseconds
     */
    public static double benchmarkAdd(WaitingList list, int nItems) {
        final int userId = RANDOM.nextInt();
        final int itemId = RANDOM.nextInt();
        final ItemRequest itemRequest = new ItemRequest(userId, itemId);
        list.clear();

        final long startTime = System.nanoTime();
        for (int i = 0; i < nItems; i++) {
            list.requestItem(itemRequest);
        }
        final long endTime = System.nanoTime();
        final long elapsed = endTime - startTime;

        return elapsed * NS_TO_MS;
    }

    /**
     * Calculates the elapsed time to determine if requests are fulfillable
     * when they are either all fulfillable or none fulfillable. The list is
     * cleared before items are added.
     *
     * @param list          determines the specific type of list to be benchmarked
     * @param nItems        number of items to be added to the list
     * @param isFulfillable if the requests are able to be fulfilled
     * @return Elapsed time in milliseconds
     */
    public static double benchmarkRequests(WaitingList list, int nItems, boolean isFulfillable) {
        final int userId = RANDOM.nextInt();
        final int itemId = RANDOM.nextInt();
        final ItemRequest itemRequest = new ItemRequest(userId, itemId);

        // fill waiting list
        list.clear();
        for (int i = 0; i < nItems; i++) {
            list.requestItem(itemRequest);
        }

        final long startTime = System.nanoTime();
        for (int i = 0; i < nItems; i++) {
            list.nextFulfillableRequest(isFulfillable);
        }
        final long endTime = System.nanoTime();
        final long elapsed = endTime - startTime;

        return elapsed * NS_TO_MS;
    }

    public static void main(String[] args) {
        WaitingList arrayList = new WaitingList(new ArrayList<>());
        WaitingList linkedList = new WaitingList(new LinkedList<>());

        final int largeNItems = 100_000;
        final int smallNItems = 5_000;

        System.out.println("ArrayList Waiting List add: " +
                benchmarkAdd(arrayList, largeNItems) + " ms");
        System.out.println("LinkedList Waiting List add: " +
                benchmarkAdd(linkedList, largeNItems) + " ms");
        System.out.println();

        System.out.println("ArrayList Waiting List fulfillable requests: " +
                benchmarkRequests(arrayList, largeNItems, true) + " ms");
        System.out.println("LinkedList Waiting List fulfillable requests: " +
                benchmarkRequests(linkedList, largeNItems, true) + " ms");
        System.out.println();

        System.out.println("ArrayList Waiting List unfulfillable requests: " +
                benchmarkRequests(arrayList, smallNItems, false) + " ms");
        System.out.println("LinkedList Waiting List unfulfillable requests: " +
                benchmarkRequests(linkedList, smallNItems, false) + " ms");
        System.out.println();
    }
}
