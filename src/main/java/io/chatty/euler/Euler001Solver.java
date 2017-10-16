package euler;

import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 1/8/17.
 */

public class Euler001Solver {

  int N;

  private Euler001Solver(int N) {
    this.N = N;
  }

  private int solve() {
    int num3s = (N - 1) / 3;
    int num5s = (N - 1) / 5;
    int num15s = (N - 1) / 15;

    return (3 * num3s * (num3s + 1) +
        5 * num5s * (num5s + 1) -
        15 * num15s * (num15s + 1)) / 2;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();

    for (int t = 0; t < T; t++) {
      Euler001Solver solver = new Euler001Solver(scanner.nextInt());
      System.out.print(solver.solve());
    }

  }

}
