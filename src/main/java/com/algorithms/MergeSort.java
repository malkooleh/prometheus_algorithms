package com.algorithms;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class MergeSort {

    private MergeSort() {
    }

    /**
     * n*lg(n)
     *
     * @param given array that should be sorted
     * @param p     the index of the first element
     * @param r     the index of the last element
     * @return sorted array
     */
    public static int[] mergeSort(int[] given, int p, int r) {

        if (r > 1 && p < r) {
            int q = (p + r - 1) / 2;
            mergeSort(given, p, q);
            mergeSort(given, q + 1, r);
            merge(given, p, q, r);
        }

        return given;
    }

    // p≤q<r
    private static void merge(int[] given, int p, int q, int r) {
        log.debug("p = {}, q = {}, r = {}", p, q, r);
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] left = new int[n1];
        int[] right = new int[n2];

        System.arraycopy(given, p - 1, left, 0, n1);
        System.arraycopy(given, q, right, 0, n2);
        log.debug("left = {}, right = {}", left, right);

        int i = 0;
        int j = 0;
        for (int k = p - 1; k < r; k++) {
            if (left.length == i) {
                given[k] = right[j];
                j++;
            } else if (right.length == j) {
                given[k] = left[i];
                i++;
            } else {
                if (left[i] <= right[j]) {
                    given[k] = left[i];
                    i++;
                } else {
                    given[k] = right[j];
                    j++;
                }
            }
        }
    }
}
