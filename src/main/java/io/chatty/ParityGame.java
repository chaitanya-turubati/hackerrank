package io.chatty;

import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 6/7/17.
 */

public class ParityGame {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Integer n = scanner.nextInt();
    Integer sum = 0;
    for (Integer i = 0; i < n; ++i) {
      sum += scanner.nextInt();
    }

    if (sum % 2 == 0) {
      System.out.println("0");
    } else {
      if (n > 1) {
        System.out.println("1");
      } else {
        System.out.println("-1");
      }
    }
  }
}
