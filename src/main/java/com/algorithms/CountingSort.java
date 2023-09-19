package com.algorithms;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CountingSort {

    private CountingSort() {
    }

    /**
     * @param given array that should be sorted
     * @return sorted array
     */
    public static int[] countingSort(int[] given) {

        int max = Arrays.stream(given).max().orElse(0);

        int[] b = new int[given.length];
        int[] c = new int[max + 1];
        for (int value : given) {
            c[value] = c[value] + 1;
        }

        for (int j = 1; j <= max; j++) {
            c[j] = c[j] + c[j - 1];
        }

        for (int i = given.length - 1; i >= 0; i--) {
            b[c[given[i]] - 1] = given[i];
            c[given[i]] = c[given[i]] - 1;
        }

        return b;
    }

    /**
     * @param given      array that should be sorted
     * @param maxElement value of max element
     * @param bit        the bit number for the values in the array to sort by
     * @return sorted array
     */
    public static CountingSortResult countingSort(String[] given, String maxElement, int bit) {

        CountingSortResult sortResult = new CountingSortResult();

        String[] b = new String[given.length];
        int maxValue = maxElement.codePointAt(0);
        int[] c = new int[maxValue + 1];
        for (String value : given) {
            int index = value.codePointAt(value.length() - bit);
            c[index] = c[index] + 1;
        }
        sortResult.setOccurrencesOfChars(Arrays.copyOf(c, maxValue + 1));

        for (int j = 1; j <= maxValue; j++) {
            c[j] = c[j] + c[j - 1];
        }

        for (int i = given.length - 1; i >= 0; i--) {
            int val = given[i].codePointAt(given[i].length() - bit);
            b[c[val] - 1] = given[i];
            c[val] = c[val] - 1;
        }
        sortResult.setSorted(b);
        log.debug("Sorted result : {},\n by bit {}", b, bit);
        return sortResult;
    }

    @Getter
    @Setter
    public static class CountingSortResult {
        private String[] sorted;
        private int[] occurrencesOfChars;
    }
}
