package com.algorithms;

import java.util.HashSet;
import java.util.Set;

public class Hash {

    private Hash() {
    }

    public static int find2NumbersWhoseSumIs(Set<Long> given) {
        HashSet<Long> set = new HashSet<>();
        long i = -1000L;
        while (i <= 1000L) {
            for (Long val : given) {
                Long y = i - val;
                if (given.contains(y)) {
                    set.add(i);
                }
            }
            i++;
        }
        return set.size();
    }
}
