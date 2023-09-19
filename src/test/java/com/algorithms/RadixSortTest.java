package com.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

class RadixSortTest {

    @Test
    void radixSort() throws IOException {
        String[] testData = TestDataHelper.readDataFromFileToStringArray("radix_sort_task_data");

        Assertions.assertThat(RadixSort.radixSort(testData, 3).getSorted())
                .isEqualTo(new String[]{"cqc", "ena", "hpo", "hzt", "lpz", "qds", "sdt", "slt", "sng", "yif"});
    }

    /* It is known that for the password generation algorithm, a database is used consisting of randomly arranged three-character strings.
     The password has the following format.

    - Password length - 7 letters
    - The first three letters are the character string that comes first in lexicographical order in the database of strings.
    - The fourth letter is the letter that occurs most frequently among all the strings.
    - The last three letters are the character string that comes last in lexicographical order in the database of strings.*/
    @Test
    void findPassword_task() throws IOException {
        String[] testData = TestDataHelper.readDataFromFileToStringArray("radix_sort_task_data");

        CountingSort.CountingSortResult sortResult = RadixSort.radixSort(testData, 3);
        int max = Arrays.stream(sortResult.getOccurrencesOfChars()).max().orElseThrow();
        String mostOccursLetter = null;
        int index = 0;
        for (int value : sortResult.getOccurrencesOfChars()) {
            if (value == max) {
                mostOccursLetter = Character.toString(index);
                break;
            }
            index++;
        }

        String[] sorted = sortResult.getSorted();
        String password = sorted[0] + mostOccursLetter + sorted[sorted.length - 1];

        Assertions.assertThat(password).isEqualTo("cqcsyif");
    }
}