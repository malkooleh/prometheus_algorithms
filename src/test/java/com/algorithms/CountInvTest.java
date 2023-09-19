package com.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class CountInvTest {

    /* The result is the count of inversions for a specific user X with respect to all other users in matrix D.*/
    @Test
    void countInversions_task1() throws IOException {
        File file = TestDataHelper.getFile("count_inversions_task1_data");

        Assertions.assertThat(CountInv.countInv(file, 2, 3)).isEqualTo(18);
        Assertions.assertThat(CountInv.countInv(file, 3, 1)).isEqualTo(32);
    }

    @Test
    void countInversions_task2() throws IOException {
        File file = TestDataHelper.getFile("count_inversions_task2_data");

        Assertions.assertThat(CountInv.countInv(file, 1, 2)).isEqualTo(8);
        Assertions.assertThat(CountInv.countInv(file, 4, 5)).isEqualTo(6);
    }
}