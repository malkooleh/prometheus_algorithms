package com.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

class TreeTest {

    /*
    Find all monotonic paths directed in the tree from top to bottom,
    such that the sum of nodes in each path equals a specific input number.
    Monotonicity means that the path should not break and must go from parent to child and so on.
     */
    @Test
    void buildBinaryTree() throws IOException {
        File file = TestDataHelper.getFile("tree_task_data");
        String given = Files.readString(file.toPath());
        LinkedList<Integer> data = parseToIntList(given);

        Tree<Integer> tree = new Tree<>(Comparator.nullsFirst(Comparator.comparingInt(value -> value)));

        tree.buildTree(data);
        /*
        Root - 55
        Paths:
            26 25
            11 15 13 12
            51
            27 11 7 3 1 2
        */
//        tree.inOrderPrint(tree.getRoot());
//        tree.preOrderPrint(tree.getRoot());
        tree.findPaths(tree.getRoot(), 51);
        Assertions.assertThat(tree.getRoot().val).isEqualTo(55);
    }

    private LinkedList<Integer> parseToIntList(String given) {
        return Arrays.stream(given.trim().split(" "))
                .mapToInt(Integer::parseInt).boxed()
                .map(integer -> integer != 0 ? integer : null)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}