package io.tschaitanya.balanced_brackets;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class BalancedBracketsMain {

  private Boolean isBalanced = true;

  private BalancedBracketsMain(String sequence) {
    Stack<Character> bracketStack = new Stack<Character>();
    for (int i = 0; i < sequence.length(); ++i) {
      char entry = sequence.charAt(i);
      if (entry == '{' || entry == '[' || entry == '(') {
        bracketStack.push(entry);
      } else {
        char matching = ' ';
        if (bracketStack.size() == 0) {
          this.isBalanced = false;
          break;
        }

        switch (bracketStack.pop()) {
          case '(':
            matching = ')';
            break;
          case '{':
            matching = '}';
            break;
          case '[':
            matching = ']';
            break;
        }

        if (matching != entry) {
          this.isBalanced = false;
          break;
        }
      }
    }
    
    if (bracketStack.size() > 0) {
      this.isBalanced = false;
    }
  }

  private String getBalanced() {
    return this.isBalanced ? "YES" : "NO";
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int T = scanner.nextInt();

    List<BalancedBracketsMain> bracketSequences = new ArrayList<BalancedBracketsMain>();

    for (int t = 0; t < T; ++t) {
      bracketSequences.add(new BalancedBracketsMain(scanner.next()));
    }

    for (int t = 0; t < T; ++t) {
      System.out.println(bracketSequences.get(t).getBalanced());
    }
  }
}
