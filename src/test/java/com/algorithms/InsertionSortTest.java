package com.algorithms;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InsertionSortTest {

    @Test
    void doSort() {
        int[] given = {9, 8, 7, 9, 5, 4, 3, 2, 1, 0};

        int[] sorted = InsertionSort.insertionSort(given);
        assertThat(sorted).isEqualTo(new int[]{0, 1, 2, 3, 4, 5, 7, 8, 9, 9});
    }
}