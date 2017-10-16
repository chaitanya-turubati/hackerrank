package io.chatty.contacts;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ContactsMain {

  private Node root = new Node();

  private void addWord(String word) {
    addChildNode(root, word, 0);
  }

  private void addChildNode(Node node, String word, int id) {
    Character currentCharacter = word.charAt(id);

    Node childNode;
    if (!node.children.containsKey(currentCharacter)) {
      childNode = new Node();
      node.children.put(currentCharacter, childNode);
    } else {
      childNode = node.children.get(currentCharacter);
    }

    childNode.count++;

    if (id < (word.length() - 1)) {
      addChildNode(childNode, word, id + 1);
    }
  }

  private int findMatch(String word) {
    Node node = findMatchNode(root, word, 0);
    return node == null ? 0 : node.count;
  }

  private Node findMatchNode(Node node, String word, int id) {
    Node childNode = node.children.get(word.charAt(id));

    if (childNode == null) {
      return null;
    } else if (id == (word.length() - 1)) {
      return childNode;
    } else {
      return findMatchNode(childNode, word, id + 1);
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ContactsMain main = new ContactsMain();

    int T = scanner.nextInt();

    for (int t = 0; t < T; ++t) {
      String operation = scanner.next();
      String word = scanner.next();

      if (operation.equals("add")) {
        main.addWord(word);
      } else if (operation.equals("find")) {
        System.out.println(main.findMatch(word));
      }
    }
  }

  private class Node {
    private Map<Character, Node> children = new TreeMap<>();
    private Integer count = 0;
  }

}
