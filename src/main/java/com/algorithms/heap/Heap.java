package com.algorithms.heap;

public interface Heap {

    default void swap(int[] given, int i, int j) {
        int temp = given[i];
        given[i] = given[j];
        given[j] = temp;
    }

    default int getParent(int x) {
        if (x == 0) {
            return 0;
        }
        return x / 2; // >>
    }

    default int getLeft(int x) {
        return 2 * x; // <<
    }

    default int getRight(int x) {
        return (2 * x) + 1;
    }
}
