package com.algorithms;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class QuickSort {

    enum PartitionImplementation {
        WITH_RIGHT_KEY, WITH_MID_KEY, WITH_LEFT_KEY
    }

    private QuickSort() {
    }

    // n^2 -> n*lg(n)
    public static int[] quickSort(int[] given, PartitionImplementation partitionImplementation) {

        AtomicInteger comparisonsCount = new AtomicInteger(0);
        return sort(given, 0, given.length - 1, comparisonsCount, partitionImplementation);
    }

    public static int countComparisons(int[] given, PartitionImplementation partitionImplementation) {

        AtomicInteger comparisonsCount = new AtomicInteger(0);
        sort(given, 0, given.length - 1, comparisonsCount, partitionImplementation);

        log.debug("Number of element comparisons = {}", comparisonsCount);
        return comparisonsCount.get();
    }

    // n*lg(n)
    public static int[] randomizedQuickSort(int[] given) {

        AtomicInteger comparisonsCount = new AtomicInteger(0);
        int[] sorted = randomizedSort(given, 0, given.length - 1, comparisonsCount);

        log.debug("Number of element comparisons = {}", comparisonsCount);
        return sorted;
    }

    /**
     * @param given                   array that should be sorted
     * @param p                       the index of the first element
     * @param r                       the index of the last element
     * @param comparisonsCount        number of element comparisons
     * @param partitionImplementation partition function to be used
     * @return sorted array
     */
    private static int[] sort(
            int[] given,
            int p,
            int r,
            AtomicInteger comparisonsCount,
            PartitionImplementation partitionImplementation
    ) {
        if (p < r) {
            int q = getFirstElementIndex(given, p, r, comparisonsCount, partitionImplementation);
            sort(given, p, q - 1, comparisonsCount, partitionImplementation);
            sort(given, q + 1, r, comparisonsCount, partitionImplementation);
        }

        return given;
    }

    private static int getFirstElementIndex(
            int[] given,
            int p,
            int r,
            AtomicInteger comparisonsCount,
            PartitionImplementation partitionImplementation
    ) {
        int q = 0;
        switch (partitionImplementation) {
            case WITH_RIGHT_KEY -> q = partition(given, p, r, comparisonsCount);
            case WITH_MID_KEY -> q = partitionWithMidKey(given, p, r, comparisonsCount);
            case WITH_LEFT_KEY -> q = partitionWithLeftKey(given, p, r, comparisonsCount);
            default -> throw new IllegalArgumentException("Unexpected 'partitionImplementation': " + partitionImplementation);
        }
        return q;
    }

    private static int partition(int[] given, int p, int r, AtomicInteger comparisonsCount) {

        int key = given[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (given[j] <= key) {
                i++;
                swap(given, i, j);
            }
        }
        comparisonsCount.addAndGet(r - p);

        given[r] = given[i + 1];
        given[i + 1] = key;

        return i + 1;
    }

    private static int partitionWithLeftKey(int[] given, int p, int r, AtomicInteger comparisonsCount) {

        swap(given, p, r);
        return partition(given, p, r, comparisonsCount);
    }

    private static int partitionWithMidKey(int[] given, int p, int r, AtomicInteger comparisonsCount) {
        int key = p;
        int m = (p + r) / 2;
        if (given[p] >= given[r]) {
            if (given[r] > given[m]) {
                key = r;
            } else if (given[p] >= given[m]) {
                key = m;
            }
        } else {
            if (given[r] < given[m]) {
                key = r;
            } else if (given[p] < given[m]) {
                key = m;
            }
        }

        swap(given, key, r);
        return partition(given, p, r, comparisonsCount);
    }

    private static void swap(int[] given, int i, int j) {
        int temp = given[i];
        given[i] = given[j];
        given[j] = temp;
    }

    /**
     * It has better performance than @see QuickSort#sort(int[], int, int)
     *
     * @param given            array that should be sorted
     * @param p                the index of the first element
     * @param r                the index of the last element
     * @param comparisonsCount number of element comparisons
     * @return sorted array
     */
    private static int[] randomizedSort(int[] given, int p, int r, AtomicInteger comparisonsCount) {

        if (p < r) {
            int q = randomizedPartition(given, p, r, comparisonsCount);
            randomizedSort(given, p, q - 1, comparisonsCount);
            randomizedSort(given, q + 1, r, comparisonsCount);
        }

        return given;
    }

    private static int randomizedPartition(int[] given, int p, int r, AtomicInteger comparisonsCount) {
        int key = getRandomNumberUsingInts(p, r + 1); // can be a performance issue
        swap(given, key, r);

        return partition(given, p, r, comparisonsCount);
    }

    private static final Random random = new Random();

    public static int getRandomNumberUsingInts(int min, int max) {
        return random.ints(min, max)
                .findFirst()
                .orElse(max);
    }
}
