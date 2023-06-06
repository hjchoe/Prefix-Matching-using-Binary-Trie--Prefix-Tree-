package Labs.Lab4;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab4 {
    static String filepath;
    static Trie t;

    class Trie {
        int size;
        int height;
        Node root;

        class Node {
            String val;
            Node left = null;
            Node right = null;

            Node(String val) {
                this.val = val;
                this.left = null;
                this.right = null;
            }

            TrieInsertResult insert(String x, int bitIndex) {
                if (val != null) {
                    // Leaf node found
                    for (int i = bitIndex; i < x.length(); i++) {
                        if (x.charAt(i) != val.charAt(i)) {
                            // First different bit found, create new internal node
                            Node newNode = new Node(null);
                            if (x.charAt(i) == '0') {
                                newNode.left = new Node(x);
                                newNode.right = this;
                            } else {
                                newNode.left = this;
                                newNode.right = new Node(x);
                            }
                            return new TrieInsertResult(newNode, true, bitIndex + 1);
                        }
                    }
                    return new TrieInsertResult(this, false, bitIndex); // x is equal to the current value, no need to insert
                } else {
                    // Internal node found
                    TrieInsertResult result;
                    if (x.charAt(bitIndex) == '0') {
                        if (left == null) {
                            left = new Node(x);
                            return new TrieInsertResult(this, true, bitIndex + 1);
                        } else {
                            result = left.insert(x, bitIndex + 1);
                            left = result.node;
                        }
                    } else {
                        if (right == null) {
                            right = new Node(x);
                            return new TrieInsertResult(this, true, bitIndex + 1);
                        } else {
                            result = right.insert(x, bitIndex + 1);
                            right = result.node;
                        }
                    }
                    return new TrieInsertResult(this, result.inserted, Math.max(bitIndex + 1, result.height));
                }
            }

            String search(String x, int bitIndex, String currentClosest) {
                if (val != null) {
                    return val;
                } else {
                    String newClosest = (currentClosest == null
                            || (val != null && val.compareTo(currentClosest) < 0 && val.compareTo(x) > 0)) ? val
                                    : currentClosest;
                    Node nextNode = null;
                    if (bitIndex < x.length()) {
                        if (x.charAt(bitIndex) == '0') {
                            if (left != null) {
                                nextNode = left;
                            } else if (right != null) {
                                nextNode = right;
                            }
                        } else {
                            if (right != null) {
                                nextNode = right;
                            } else if (left != null) {
                                nextNode = left;
                            }
                        }
                    }

                    if (nextNode == null) {
                        return newClosest;
                    } else {
                        return nextNode.search(x, bitIndex + 1, newClosest);
                    }
                }
            }
        }

        private class TrieInsertResult {
            Node node;
            boolean inserted;
            int height;

            public TrieInsertResult(Node node, boolean inserted, int height) {
                this.node = node;
                this.inserted = inserted;
                this.height = height;
            }
        }

        Trie() {
            size = 0;
            height = 0;
            root = null;
        }

        Boolean insert(String st) {
            if (root == null) {
                root = new Node(st);
                size++;
                height++;
                return true;
            } else {
                TrieInsertResult result = root.insert(st, 0);
                root = result.node;
                if (result.inserted) {
                    size++;
                    height = Math.max(height, result.height + 1);
                }
                return result.inserted;
            }
        }

        String search(String st) {
            if (root == null) {
                return null;
            }
            return root.search(st, 0, null);
        }

        public List<String> trieToList() {
            List<String> strings = new ArrayList<>();
            collectStrings(root, strings);
            return strings;
        }

        private void collectStrings(Node node, List<String> strings) {
            if (node != null) {
                if (node.val != null) {
                    strings.add(node.val);
                }
                collectStrings(node.left, strings);
                collectStrings(node.right, strings);
            }
        }

        String largest() {
            Node current = root;
            while (current != null && current.val == null) {
                current = current.right;
            }
            return current == null ? null : current.val;
        }

        String smallest() {
            Node current = root;
            while (current != null && current.val == null) {
                current = current.left;
            }
            return current == null ? null : current.val;
        }

        int size() {
            return this.size;
        }

        int height() {
            return this.height;
        }

        String trieToString() {
            StringBuilder sb = new StringBuilder();
            List<Node> currentLevel = new ArrayList<>();
            currentLevel.add(root);

            while (!currentLevel.isEmpty()) {
                List<Node> nextLevel = new ArrayList<>();
                for (Node node : currentLevel) {
                    if (node != null) {
                        sb.append("[").append(node.val == null ? "-" : node.val).append(", ");
                        if (node.left != null) {
                            sb.append("0");
                            nextLevel.add(node.left);
                        } else {
                            sb.append("-");
                        }
                        sb.append(", ");
                        if (node.right != null) {
                            sb.append("1");
                            nextLevel.add(node.right);
                        } else {
                            sb.append("-");
                        }
                        sb.append("]");
                    } else {
                        sb.append("[----]");
                    }
                    sb.append(" ");
                }
                sb.append("\n");
                currentLevel = nextLevel;
            }
            sb.append("\n");
            return sb.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        filepath = args[0];
        Lab4 lab = new Lab4();
        t = lab.new Trie();

        Scanner s = new Scanner(new FileReader(filepath));
        StringBuilder sb = new StringBuilder();
        StringBuilder sbgraphics = new StringBuilder();

        while (s.hasNextLine()) {
            String[] command = s.nextLine().split(" ");
            switch (command[0]) {
                case "insert":
                    t.insert(command[1]);
                    sbgraphics.append("insert " + command[1] + "\n" + t.trieToString());
                    break;
                case "search":
                    sb.append(t.search(command[1]) + "\n");
                    break;
                case "print":
                    List<String> print = t.trieToList();
                    sb.append(print.get(0));
                    for (int i = 1; i < print.size(); i++) {
                        sb.append(", " + print.get(i));
                    }
                    sb.append("\n");
                    break;
                case "largest":
                    sb.append(t.largest() + "\n");
                    break;
                case "smallest":
                    sb.append(t.smallest() + "\n");
                    break;
                case "height":
                    sb.append(t.height() + "\n");
                    break;
                case "size":
                    sb.append(t.size() + "\n");
                    break;
                default:
                    break;
            }
        }
        System.out.println(sb.toString());
        FileWriter fw = new FileWriter("graphics.txt");
        fw.write(sbgraphics.toString());
        fw.close();
    }
}
