package com.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

class HashTest {


    /*
    Calculate the number of possible distinct values of S in the interval [-1000, 1000] (inclusive),
    such that in the given file, there are two different values x and y satisfying the condition x + y = S.
    */
    @Test
    void findNumbers() throws IOException {
        HashSet<Long> data = (HashSet<Long>) TestDataHelper.readDataFromFileToLongsSet("hash_task_data");

        int countNumbersWhoseSumIs = Hash.find2NumbersWhoseSumIs(data);
        Assertions.assertThat(countNumbersWhoseSumIs).isEqualTo(22);
    }

    /*
    Нехай в нас є хеш-таблиця з відкритою адресацією та коефіцієнтом заповнення α.
    Знайдіть ненульове значення  α, при якому середня кількість досліджень
    при невдалому пошуку буде вдвічі більшою за середню кількість досліджень у випадку вдалого пошуку.
    Введіть отримане число із точністю до 3 знаків після коми.
    *//*
    @Test
    void fillFactor() {
        double fillFactor = getFillFactor();

        Assertions.assertThat((int) (fillFactor * 1000)).isEqualTo(716);
    }

    private double getFillFactor() {
        double i = 0.6;
        while (i < 1) {
            i += 0.001;

            if ((int) ((1 / (1 - i)) / (1 / i * Math.log(1 / (1 - i)))) == 2) {
                return i;
            }
        }

        return 0;
    }*/
}