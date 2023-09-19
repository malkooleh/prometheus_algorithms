package com.algorithms;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class QuickSortTest {

    @Test
    void quickSort() {
        Instant start1 = Instant.now();
        int[] given = {9, 3, 7, 1, 5, 4, 8, 2, 9, 0};
        int[] sorted = QuickSort.quickSort(given, QuickSort.PartitionImplementation.WITH_RIGHT_KEY);
        assertThat(sorted).isEqualTo(new int[]{0, 1, 2, 3, 4, 5, 7, 8, 9, 9});
        Duration duration1 = Duration.between(start1, Instant.now());
        System.out.println("duration ms = " + duration1.toMillis());

        Instant start2 = Instant.now();
        given = new int[]{9, 3, 7, 1, 5, 4, 8, 2, 9, 0};
        int[] res = QuickSort.randomizedQuickSort(given);
        assertThat(res).isEqualTo(new int[]{0, 1, 2, 3, 4, 5, 7, 8, 9, 9});
        Duration duration2 = Duration.between(start2, Instant.now());
        System.out.println("duration ms = " + duration2.toMillis());
    }

    @Test
    void countingComparisons_usingDifferentImplementationsOfPartitionFunction() throws IOException {
        int[] array = TestDataHelper.readDataFromFileToIntArray("quick_sort_data", 1);

        // 656 comparisons for sort using QuickSort.partition
        // 582 comparisons for sort using QuickSort.partitionWithLeftKey
        // 586 comparisons for sort using QuickSort.partitionWithMidKey
        assertThat(QuickSort.countComparisons(Arrays.copyOf(array, array.length), QuickSort.PartitionImplementation.WITH_RIGHT_KEY))
                .isEqualTo(656);
        assertThat(QuickSort.countComparisons(Arrays.copyOf(array, array.length), QuickSort.PartitionImplementation.WITH_LEFT_KEY))
                .isEqualTo(582);
        assertThat(QuickSort.countComparisons(Arrays.copyOf(array, array.length), QuickSort.PartitionImplementation.WITH_MID_KEY))
                .isEqualTo(586);
    }
}