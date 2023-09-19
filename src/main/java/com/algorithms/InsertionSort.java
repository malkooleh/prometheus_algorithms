package com.algorithms;

class InsertionSort {

    private InsertionSort() {
    }

    /**
     * n^2
     * @param given array that should be sorted
     * @return sorted array
     */
    public static int[] insertionSort(int[] given) {

        for (int i = 1; i <= given.length - 1; i++) {
            int key = given[i];
            int j = i - 1;
            while (j >= 0 && given[j] > key) {
                given[j + 1] = given[j];
                j--;
            }
            given[j + 1] = key;
        }

        return given;
    }
}
