package io.chatty;

import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 7/7/17.
 */
public class Nitika {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    for (int n = scanner.nextInt(); n >= 0; n--) {
      String fullName = scanner.nextLine();
      if (fullName.equals("")) continue;

      String[] names = fullName.split(" ");
      String formattedName = "";
      for (int j = 0; j < names.length - 1; ++j) {
        formattedName += names[j].substring(0, 1).toUpperCase() + ". ";
      }

      formattedName += names[names.length - 1].substring(0, 1).toUpperCase();
      formattedName += names[names.length - 1].substring(1).toLowerCase();

      System.out.println(formattedName);
    }
  }

}
