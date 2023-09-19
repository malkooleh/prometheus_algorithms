package com.algorithms.heap;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class MinHeap implements Heap {

    private int[] heap;
    private int length;

    public MinHeap(int[] given) {
        this.heap = buildHeap(given);
        this.length = this.heap.length;
    }

    public void rebuild() {
        buildHeap(this.heap);
    }

    private int[] buildHeap(int[] given) {
        for (int i = given.length / 2; i >= 1; i--) {
            minHeapify(given, i);
        }

        return given;
    }

    public int getHeapMinimum() {
        return this.heap[0];
    }

    private void minHeapify(int[] given, int x) {
        int l = getLeft(x);
        int r = getRight(x);

        int min = x;
        if (l <= given.length && given[l - 1] < given[x - 1]) {
            min = l;
        }
        if (r <= given.length && given[r - 1] < given[min - 1]) {
            min = r;
        }

        if (min != x) {
            swap(given, min - 1, x - 1);
            minHeapify(given, min);
        }
    }

    public int heapExtractMin() {
        if (this.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Array has no elements");
        }

        int min = heap[0];
        swap(heap, 0, this.length - 1);
        heap = Arrays.copyOf(heap, --this.length);

        minHeapify(heap, 1);
        return min;
    }

    public void heapIncreaseKey(int x, int key) {
        heap[x - 1] = key;
        while (x > 1 && heap[getParent(x) - 1] > heap[x - 1]) {
            swap(heap, x - 1, getParent(x) - 1);
            x = getParent(x);
        }
    }

    public void minHeapInsert(int key) {
        this.heap = Arrays.copyOf(this.heap, ++this.length);
        heapIncreaseKey(this.length, key);
    }
}
