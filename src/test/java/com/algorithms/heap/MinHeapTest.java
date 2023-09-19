package com.algorithms.heap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MinHeapTest {

    @Test
    void buildHeap() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};

        MinHeap minHeap = new MinHeap(given);
        Assertions.assertThat(minHeap.getHeap()).isEqualTo(new int[]{1, 2, 3, 4, 7, 9, 10, 14, 8, 16});
    }

    @Test
    void heapExtractMin() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        MinHeap minHeap = new MinHeap(given);
        Assertions.assertThat(minHeap.getHeap()).isEqualTo(new int[]{1, 2, 3, 4, 7, 9, 10, 14, 8, 16});

        int min = minHeap.heapExtractMin();
        Assertions.assertThat(minHeap.getHeap()).isEqualTo(new int[]{2, 4, 3, 8, 7, 9, 10, 14, 16});
        Assertions.assertThat(min).isEqualTo(1);
    }

    @Test
    void heapIncreaseKey() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        MinHeap minHeap = new MinHeap(given);
        Assertions.assertThat(minHeap.getHeap()).isEqualTo(new int[]{1, 2, 3, 4, 7, 9, 10, 14, 8, 16});

        minHeap.heapIncreaseKey(9, 3);
        Assertions.assertThat(minHeap.getHeap()).isEqualTo(new int[]{1, 2, 3, 3, 7, 9, 10, 14, 4, 16});
    }

    @Test
    void minHeapInsert() {
        int[] given = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        MinHeap minHeap = new MinHeap(given);
        Assertions.assertThat(minHeap.getHeap()).isEqualTo(new int[]{1, 2, 3, 4, 7, 9, 10, 14, 8, 16});

        minHeap.minHeapInsert(3);
        Assertions.assertThat(minHeap.getHeap()).isEqualTo(new int[]{1, 2, 3, 4, 3, 9, 10, 14, 8, 16, 7});
    }
}