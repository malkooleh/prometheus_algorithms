package com.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CountingSortTest {

    @Test
    void countingSort() {
        int[] given = {2, 5, 3, 0, 2, 3, 0, 3};
        int[] sorted = CountingSort.countingSort(given);

        Assertions.assertThat(sorted).isEqualTo(new int[]{0, 0, 2, 2, 3, 3, 3, 5});
    }
}