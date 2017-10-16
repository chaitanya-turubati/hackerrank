package io.chatty;

import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 7/7/17.
 */

public class PalindromicTable {

  private int[][] A;
  private int n;
  private int m;
  private int nm;

  public static void main(String[] args) {
    PalindromicTable solver = new PalindromicTable();
    solver.scanInput();
  }

  private void scanInput() {
    Scanner scanner = new Scanner(System.in);

    n = scanner.nextInt();
    m = scanner.nextInt();

    nm = n * m;

    A = new int[n][m];

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < m; ++j) {
        A[i][j] = scanner.nextInt();
      }
    }
  }

}
