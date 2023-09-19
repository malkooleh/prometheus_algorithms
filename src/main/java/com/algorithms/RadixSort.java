package com.algorithms;

public class RadixSort {

    private RadixSort() {
    }

    /**
     * d(n + k), k - the maximum possible value for an element in a certain bit (d)
     *
     * @param given array that should be sorted
     * @param d     bit size of the values in the array
     * @return sorted array
     */
    public static CountingSort.CountingSortResult radixSort(String[] given, int d) {

        CountingSort.CountingSortResult sortResult = new CountingSort.CountingSortResult();
        String maxElement = "z";
        int[] occurrencesOfChars = new int[maxElement.codePointAt(0)];

        for (int i = 1; i <= d; i++) {
            sortResult = CountingSort.countingSort(given, maxElement, i);
            given = sortResult.getSorted();

            if (sortResult.getOccurrencesOfChars() != null) {
                for (int j = 0; j < sortResult.getOccurrencesOfChars().length - 1; j++) {
                    occurrencesOfChars[j] = occurrencesOfChars[j] + sortResult.getOccurrencesOfChars()[j];
                }
            }
        }
        sortResult.setOccurrencesOfChars(occurrencesOfChars);

        return sortResult;
    }
}
