package com.algorithms.heap;

import com.algorithms.TestDataHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class HeapTest {

    /*
    We need to provide the values of medians on the x-iteration
    during work on the given array (iterations are indexed from 1 to ...).
    If there are two medians, separate them with a single space; otherwise, enter one number.
    (selected for test iterations 51 and 94)
     */
    @Test
    void practice_task() throws IOException {
        List<Integer> testData = TestDataHelper.readDataFromFileToList("heap_task_data");
        Integer elementsCount = testData.remove(0);
        System.out.println("Elements count : " + elementsCount + "\n");

        MaxHeap maxHeap = new MaxHeap(new int[]{}); // values from high to low
        MinHeap minHeap = new MinHeap(new int[]{}); // values from low to high
        int iterationCount = 1;
        for (Integer val : testData) {
            if (maxHeap.getLength() != 0 && val < maxHeap.getHeapMaximum()) {
                maxHeap.maxHeapInsert(val);
                maxHeap.rebuild();
            } else {
                minHeap.minHeapInsert(val);
                minHeap.rebuild();
            }

            int difference = minHeap.getLength() - maxHeap.getLength();
            if (difference == -2) {
                int max = maxHeap.heapExtractMax();
                minHeap.minHeapInsert(max);
                minHeap.rebuild();
            } else if (difference == 2) {
                int min = minHeap.heapExtractMin();
                maxHeap.maxHeapInsert(min);
                maxHeap.rebuild();
            }

            int[] median = getMedian(minHeap, maxHeap);

            if (iterationCount == 51) {
                Assertions.assertThat(median).isEqualTo(new int[]{26});
                System.out.println("Iteration : " + iterationCount
                        + "\nH_low : " + Arrays.toString(maxHeap.getHeap())
                        + "\nH_high : " + Arrays.toString(minHeap.getHeap()) + "\n");
            }
            if (iterationCount == 94) {
                Assertions.assertThat(median).isEqualTo(new int[]{47, 48});
                System.out.println("Iteration : " + iterationCount
                        + "\nMedians : " + (median.length > 1 ? (median[0] + " " + median[1]) : median[0])
                        + "\nH_low : " + Arrays.toString(maxHeap.getHeap())
                        + "\nH_high : " + Arrays.toString(minHeap.getHeap()) + "\n");
            }
            iterationCount++;
        }
    }

    private int[] getMedian(MinHeap minHeap, MaxHeap maxHeap) {
        int[] median;
        int difference = minHeap.getLength() - maxHeap.getLength();

        if (difference > 0) {
            median = new int[]{minHeap.getHeapMinimum()};
        } else if (difference < 0) {
            median = new int[]{maxHeap.getHeapMaximum()};
        } else {
            median = new int[]{maxHeap.getHeapMaximum(), minHeap.getHeapMinimum()};
        }

        return median;
    }
}