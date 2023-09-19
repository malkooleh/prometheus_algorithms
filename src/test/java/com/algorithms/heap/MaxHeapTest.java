package com.algorithms.heap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MaxHeapTest {

    @Test
    void buildHeap() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};

        MaxHeap maxHeap = new MaxHeap(given);
        Assertions.assertThat(maxHeap.getHeap()).isEqualTo(new int[]{16, 14, 10, 8, 7, 9, 3, 2, 4, 1});
    }

    @Test
    void heapExtractMax() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        MaxHeap maxHeap = new MaxHeap(given);
        Assertions.assertThat(maxHeap.getHeap()).isEqualTo(new int[]{16, 14, 10, 8, 7, 9, 3, 2, 4, 1});

        int max = maxHeap.heapExtractMax();
        Assertions.assertThat(maxHeap.getHeap()).isEqualTo(new int[]{14, 8, 10, 4, 7, 9, 3, 2, 1});
        Assertions.assertThat(max).isEqualTo(16);
    }

    @Test
    void heapIncreaseKey() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        MaxHeap maxHeap = new MaxHeap(given);
        Assertions.assertThat(maxHeap.getHeap()).isEqualTo(new int[]{16, 14, 10, 8, 7, 9, 3, 2, 4, 1});

        maxHeap.heapIncreaseKey(9, 15);
        Assertions.assertThat(maxHeap.getHeap()).isEqualTo(new int[]{16, 15, 10, 14, 7, 9, 3, 2, 8, 1});
    }

    @Test
    void maxHeapInsert() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        MaxHeap maxHeap = new MaxHeap(given);
        Assertions.assertThat(maxHeap.getHeap()).isEqualTo(new int[]{16, 14, 10, 8, 7, 9, 3, 2, 4, 1});

        maxHeap.maxHeapInsert(15);
        Assertions.assertThat(maxHeap.getHeap()).isEqualTo(new int[]{16, 15, 10, 8, 14, 9, 3, 2, 4, 1, 7});
    }

//    @Test
//    void maxHeap_heapSort() {
//        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
//        MaxHeap maxHeap = new MaxHeap(given);
//
//        int[] sorted = maxHeap.heapSort();
//        Assertions.assertThat(sorted).isEqualTo(new int[]{1, 2, 3, 4, 7, 8, 9, 10, 14, 16});
//    }
}