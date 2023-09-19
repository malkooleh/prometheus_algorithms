package com.algorithms;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountInv {

    private CountInv() {
    }

    public static int countInv(File file, int userId1, int userId2) throws IOException {
        Stream<String> lines = Files.lines(file.toPath());
        List<String> ratingLines = lines.collect(Collectors.toList()); // we need mutable kind of List
        lines.close();

        List<Integer> mainInfo = getMainInfo(ratingLines);
        int usersCount = mainInfo.get(0);
        int filmsCount = mainInfo.get(1);

        Map<Integer, List<Integer>> ratingsByUser = getRatingsByUser(ratingLines);
        int[] combinedRatings = combineRatingsIntoArray(ratingsByUser.get(userId1), ratingsByUser.get(userId2), filmsCount);

        return count(combinedRatings, 0);
    }

    private static int count(int[] given, int count) {
        if (given.length == 1) {
            return 0;
        }

        int m = given.length / 2;
        int[] left = new int[m];
        int[] right = new int[given.length - m];
        System.arraycopy(given, 0, left, 0, m);
        if (given.length - m >= 0) System.arraycopy(given, m, right, 0, given.length - m);

        int leftCount = count(left, count);
        int rightCount = count(right, count);
        int splitCount = mergeAndCountSplitInv(given, left, right);

        return leftCount+ rightCount + splitCount;
    }

    private static int mergeAndCountSplitInv(int[] given, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int res = 0;
        for (int k = 0; k < left.length + right.length; k++) {
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
                    res = res + (left.length - i);
                }
            }
        }

        return res;
    }

    private static int[] combineRatingsIntoArray(List<Integer> ratingsUser1, List<Integer> ratingsUser2, int filmsCount) {
        int[] ratings = new int[filmsCount];
        // index - the film rating starts with 1
        ratingsUser1.forEach(index -> ratings[index - 1] = ratingsUser2.get(ratingsUser1.indexOf(index)));

        return ratings;
    }

    private static List<Integer> getMainInfo(List<String> ratingLines) {
        return Arrays.stream(ratingLines.remove(0).trim().split(" "))
                .map(Integer::parseInt)
                .toList();
    }

    private static Map<Integer, List<Integer>> getRatingsByUser(List<String> ratingLines) {
        return ratingLines.stream()
                .map(s -> Arrays.stream(s.trim().split(" "))
                        .mapToInt(Integer::parseInt).boxed().collect(Collectors.toList())) // we need mutable kind of List
                .collect(Collectors.toMap(
                        integers -> integers.remove(0),
                        Function.identity()
                ));
    }
}
