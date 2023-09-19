package com.algorithms.heap;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class MaxHeap implements Heap {

    private int[] heap;
    private int length;

    public MaxHeap(int[] given) {
        this.heap = buildHeap(given);
        this.length = this.heap.length;
    }

    public void rebuild() {
        buildHeap(this.heap);
    }

    private int[] buildHeap(int[] given) {
        for (int i = given.length / 2; i >= 1; i--) {
            maxHeapify(given, i);
        }

        return given;
    }

    public int getHeapMaximum() {
        return this.heap[0];
    }

    public int heapExtractMax() {
        if (this.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Array has no elements");
        }

        int min = this.heap[0];
        swap(this.heap, 0, this.length - 1);
        this.heap = Arrays.copyOf(this.heap, --this.length);

        maxHeapify(this.heap, 1);
        return min;
    }

    public void heapIncreaseKey(int x, int key) {
        this.heap[x - 1] = key;
        while (x > 1 && this.heap[getParent(x) - 1] < this.heap[x - 1]) {
            swap(this.heap, x - 1, getParent(x) - 1);
            x = getParent(x);
        }
    }

    public void maxHeapInsert(int key) {
        this.heap = Arrays.copyOf(this.heap, ++this.length);
        heapIncreaseKey(this.length, key);
    }

    private void maxHeapify(int[] given, int x) {
        int l = getLeft(x);
        int r = getRight(x);

        int largest = x;
        if (l <= given.length && given[l - 1] > given[x - 1]) {
            largest = l;
        }
        if (r <= given.length && given[r - 1] > given[largest - 1]) {
            largest = r;
        }

        if (largest != x) {
            swap(given, largest - 1, x - 1);
            maxHeapify(given, largest);
        }
    }

//    public int[] heapSort() {
//        for (int i = this.length; i >= 2; i--) {
//            swap(this.heap, 0, i - 1);
//
//            this.length--;
//            maxHeapify(this.heap, 1);
//        }
//
//        return this.heap;
//    }
}
