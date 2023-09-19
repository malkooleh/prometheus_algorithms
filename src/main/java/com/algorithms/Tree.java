package com.algorithms;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

// left < root < right
@Slf4j
public class Tree<K> {

    @Getter
    private Node<K> root;
    private final Comparator<K> comparator;

    public Tree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    // task 1
    public void buildTree(LinkedList<K> data) {
        preOrderTreeWalk(data, root);
        rebuildToBinarySearchTree();
    }

    private void rebuildToBinarySearchTree() {
        List<K> inorderTreeValues = inorderTreeWalk(new LinkedList<>(), this.getRoot());
        inorderTreeValues.removeIf(Objects::isNull);
        inorderTreeValues.sort(comparator);
//        log.debug(String.valueOf(inorderTreeValues));

        inorderSetSortedValues((LinkedList<K>) inorderTreeValues, root);
        log.debug("Root element is {}", root);
    }

    public void findPaths(Node<K> node, int sum) {
        List<List<Node<K>>> paths = new ArrayList<>();
        findPaths(node, sum, paths);

        for (List<Node<K>> path : paths) {
            for (Node<K> integer : path) {
                System.out.print(integer.val + " ");
            }
            System.out.println();
        }
    }

    private void findPaths(Node<K> node, int sum, List<Node<K>> path, List<List<Node<K>>> paths) {
        if (node == null)
            return;

        path.add(node);

        if (sum == (int) node.val) {
            paths.add(new ArrayList<>(path));
        } else {
            findPaths(node.left, sum - (int) node.val, path, paths);
            findPaths(node.right, sum - (int) node.val, path, paths);
        }

        path.remove(path.size() - 1);
    }

    private void findPaths(Node<K> node, int sum, List<List<Node<K>>> paths) {
        if (node == null)
            return;

        findPaths(node.left, sum, paths);
        findPaths(node.right, sum, paths);
        findPaths(node, sum, new ArrayList<>(), paths);
    }

    public void insert(K value) {
        if (value == null) return;
        Node<K> x = this.root;
        Node<K> y = null;
        Comparator<? super K> cpr = comparator;
        while (x != null) {
            y = x;
            if (cpr.compare(value, x.val) <= 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        Node<K> node = new Node<>(value, y, null, null);
        // empty tree
        if (y == null) {
            this.root = node;
        } else if (cpr.compare(node.val, y.val) <= 0) {
            y.left = node;
        } else {
            y.right = node;
        }
    }

    /**
     * @param val key that should be removed
     * @return removed key
     * @throws NullPointerException – if val is null
     */
    public Node<K> remove(@Nonnull K val) {
        Node<K> given = iterativeSearch(val);
        Objects.requireNonNull(given);

        Node<K> y;// will be deleted
        Node<K> x;
        if (given.left == null && given.right == null) {
            y = given;
        } else {
            y = treeSuccessor(given);
        }
        Objects.requireNonNull(y);
        if (y.left != null) {
            x = y.left;
        } else {
            x = y.right;
        }
        if (x != null) {
            x.parent = y.parent;
        }
        if (y.parent == null) {
            this.root = x;
        } else if (y.equals(y.parent.left)) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        if (y != given) {
            given.val = y.val;
        }
        return y;
    }

    // balanced tree - O(h) = lg(n)
    private Node<K> search(Node<K> x, K k) {
        if (x == null || Objects.equals(k, x.val)) {
            return x;
        }
        if (comparator.compare(k, x.val) <= 0) {
            return search(x.left, k);
        } else {
            return search(x.right, k);
        }
    }

    // balanced tree - O(h) = lg(n)
    private Node<K> iterativeSearch(K k) {
        Node<K> x = this.root;
        while (x != null && k != x.val) {
            if (comparator.compare(k, x.val) <= 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        return x;
    }

    // next Node with the lowest key greater than key[node](node.val)
    private Node<K> treeSuccessor(Node<K> node) {
        Objects.requireNonNull(node);
        Node<K> y;
        if (node.right != null) {
            return treeMinimum(node.right);
        }
        y = node.parent;
        while (y != null && node.equals(y.right)) {
            node = y;
            y = y.parent;
        }
        return y;
    }

    private static <K> Node<K> treeMinimum(Node<K> x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private static <K> Node<K> treeMaximum(Node<K> x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    private void preOrderTreeWalk(LinkedList<K> data, Node<K> node) {
        if (data.isEmpty()) return;

        K x = data.pollFirst();
        if (x != null) {
            if (node == null) {
                node = new Node<>(x);
                this.root = node;
            }
            node.val = x;
            node.left = new Node<>(node);
            preOrderTreeWalk(data, node.left);
            node.right = new Node<>(node);
            preOrderTreeWalk(data, node.right);
        } else {
            if (node.isLeft()) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }

    // inorderTreeWalk
    private void inorderSetSortedValues(LinkedList<K> data, Node<K> node) {
        if (data.isEmpty()) return;

        if (node != null && node.val != null) {
            inorderSetSortedValues(data, node.left);
            node.val = data.pollFirst();
            inorderSetSortedValues(data, node.right);
        }
    }

    private List<K> inorderTreeWalk(LinkedList<K> data, Node<K> x) {
        if (x != null) {
            inorderTreeWalk(data, x.left);
            data.add(x.val);
            inorderTreeWalk(data, x.right);
        }

        return data;
    }

    void inOrderPrint(Node<K> x) {
        if (x != null) {
            inOrderPrint(x.left);
            log.debug(String.valueOf(x));
            inOrderPrint(x.right);
        }
    }

    // Function to do preorder traversal of BST
    void preOrderPrint(Node<K> x) {
        if (x != null) {
            log.debug(String.valueOf(x));
            preOrderPrint(x.left);
            preOrderPrint(x.right);
        }
    }

    @AllArgsConstructor
    static class Node<K> {
        K val;
        Node<K> parent;
        Node<K> left;
        Node<K> right;

        public Node(K val) {
            this.val = val;
            parent = left = right = null;
        }

        public Node(Node<K> parent) {
            this.parent = parent;
            val = null;
            left = right = null;
        }

        boolean isLeft() {
            return this.parent != null && (this.equals(this.parent.left));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            if (val != node.val) return false;
            return Objects.equals(parent, node.parent);
        }

        @Override
        public int hashCode() {
            int result = val.hashCode();
            result = 31 * result + (parent != null ? parent.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", parent=" + (parent != null ? parent.val : null) +
                    ", left=" + (left != null ? left.val : null) +
                    ", right=" + (right != null ? right.val : null) +
                    '}';
        }
    }
}
