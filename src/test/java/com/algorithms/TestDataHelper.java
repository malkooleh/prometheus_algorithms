package com.algorithms;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestDataHelper {

    public static File getFile(String fileName) {
        ClassLoader classLoader = TestDataHelper.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    }

    public static int[] readDataFromFileToIntArray(String fileName) throws IOException {
        ClassLoader classLoader = TestDataHelper.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        IntStream lines = Files.lines(file.toPath()).mapToInt(Integer::parseInt);
        int[] array = lines.toArray();
        lines.close();

        return array;
    }

    public static int[] readDataFromFileToIntArray(String fileName, int skipElements) throws IOException {
        ClassLoader classLoader = TestDataHelper.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        IntStream lines = Files.lines(file.toPath()).mapToInt(Integer::parseInt);
        int[] array = lines.skip(skipElements).toArray();
        lines.close();

        return array;
    }

    public static String[] readDataFromFileToStringArray(String fileName) throws IOException {
        ClassLoader classLoader = TestDataHelper.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        Stream<String> lines = Files.lines(file.toPath());
        List<String> values = lines.map(s -> s.trim().toLowerCase(Locale.ROOT)).toList();
        lines.close();

        return values.toArray(new String[0]);
    }

    public static List<Integer> readDataFromFileToList(String fileName) throws IOException {
        ClassLoader classLoader = TestDataHelper.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        IntStream lines = Files.lines(file.toPath()).mapToInt(Integer::parseInt);
        List<Integer> dataList = lines.boxed().collect(Collectors.toList());
        lines.close();

        return dataList;
    }

    public static List<BigInteger> readDataFromFileToBigIntList(String fileName) throws IOException {
        ClassLoader classLoader = TestDataHelper.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        Stream<BigInteger> lines = Files.lines(file.toPath()).map(BigInteger::new).toList().stream();
        List<BigInteger> dataList = lines.collect(Collectors.toList());
        lines.close();

        return dataList;
    }

    public static Set<Long> readDataFromFileToLongsSet(String fileName) throws IOException {
        ClassLoader classLoader = TestDataHelper.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        Stream<Long> lines = Files.lines(file.toPath()).map(Long::parseLong).toList().stream();
        Set<Long> dataList = lines.collect(Collectors.toSet());
        lines.close();

        return dataList;
    }
}
