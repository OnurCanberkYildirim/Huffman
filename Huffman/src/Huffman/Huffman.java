/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Huffman;

import java.util.*;

public class Huffman {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        char[] characters = new char[parts.length / 2];
        int[] frequencies = new int[parts.length / 2];
        for (int i = 0; i < parts.length; i += 2) {
            characters[i / 2] = parts[i].charAt(0);
            frequencies[i / 2] = Integer.parseInt(parts[i + 1]);
        }

        Node root = buildTree(characters, frequencies);

        Map<Character, String> encodings = new HashMap<>();
        encode(root, "", encodings);

        StringBuilder compressed = new StringBuilder();
        for (char c : characters) {
            compressed.append(encodings.get(c) + " ");
        }
        System.out.println(compressed);
    }

    private static Node buildTree(char[] characters, int[] frequencies) {

        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.frequency - n2.frequency;
            }
        });

        for (int i = 0; i < characters.length; i++) {
            queue.add(new Node(characters[i], frequencies[i], null, null));
        }

        while (queue.size() > 1) {
            Node n1 = queue.poll();
            Node n2 = queue.poll();
            Node parent = new Node('\0', n1.frequency + n2.frequency, n1, n2);
            queue.add(parent);
        }

        return queue.poll();
    }

    private static void encode(Node node, String encoding, Map<Character, String> encodings) {
        if (node.left == null && node.right == null) {

            encodings.put(node.character, encoding);
        } else {

            encode(node.left, encoding + "0", encodings);
            encode(node.right, encoding + "1", encodings);
        }
    }

    private static class Node {

        char character;
        int frequency;
        Node left;
        Node right;

        public Node(char character, int frequency, Node left, Node right) {
            this.character = character;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
    }
}
