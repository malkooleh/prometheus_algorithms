package com.algorithms;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MergeSortTest {

    @Test
    void mergeSort() {
        int[] given = {8, 7, 6, 5, 4, 3, 2, 1};

        int firstElement = 1;
        int[] sorted = MergeSort.mergeSort(given, firstElement, given.length);
        assertThat(sorted).isEqualTo(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
    }
}