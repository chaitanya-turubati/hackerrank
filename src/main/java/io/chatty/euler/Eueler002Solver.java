package euler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 3/8/17.
 */
public class Eueler002Solver {

  private static class TwoTwoMatrix {

    long a11, a12, a21, a22;

    public void multiply(TwoTwoMatrix other) {
      long a11 = this.a11 * other.a11 + this.a12 * other.a21;
      long a12 = this.a11 * other.a12 + this.a12 * other.a22;
      long a21 = this.a21 * other.a11 + this.a22 * other.a21;
      long a22 = this.a21 * other.a12 + this.a22 * other.a22;

      this.a11 = a11;
      this.a12 = a12;
      this.a21 = a21;
      this.a22 = a22;
    }

    public TwoTwoMatrix clone() {
      TwoTwoMatrix matrix = new TwoTwoMatrix();
      matrix.a11 = a11;
      matrix.a12 = a12;
      matrix.a21 = a21;
      matrix.a22 = a22;

      return matrix;
    }

    public static TwoTwoMatrix fibonacci() {
      TwoTwoMatrix matrix = new TwoTwoMatrix();
      matrix.a11 = 1;
      matrix.a12 = 1;
      matrix.a21 = 1;
      matrix.a22 = 0;

      return matrix;
    }

    public static TwoTwoMatrix identity() {
      TwoTwoMatrix matrix = new TwoTwoMatrix();
      matrix.a11 = 1;
      matrix.a12 = 0;
      matrix.a21 = 0;
      matrix.a22 = 1;

      return matrix;
    }

    public long getFib() {
      return a11 + a12;
    }

    private void print() {
      System.out.printf("|%d\t%d|\n|%d\t%d|\n\n", this.a11, this.a12, this.a21, this.a22);
    }
  }

  private List<TwoTwoMatrix> powerCache = new ArrayList<>();

  {
    powerCache.add(TwoTwoMatrix.fibonacci());

    for (int i = 0; i < 6; i++) {
      TwoTwoMatrix matrix = powerCache.get(powerCache.size() - 1).clone();
      matrix.multiply(matrix);
      powerCache.add(matrix);
    }
  }

  private long find(long n) {
    boolean[] coeffs = new boolean[7];

    int id = 1;
    for (; id < 7; ++id) {
      if (n < powerCache.get(id).getFib()) {
        break;
      }
    }

    id--;

    TwoTwoMatrix matrix = powerCache.get(id).clone();

    for (int i = 6; i > id; i--) {
      coeffs[i] = false;
    }

    coeffs[id] = true;
    if (id == 6) {
      coeffs[5] = false;
      id = 4;
    } else {
      id--;
    }

    for (; id >= 0; id--) {
      TwoTwoMatrix matrixTemp = matrix.clone();
      matrixTemp.multiply(powerCache.get(id));

      if (matrixTemp.getFib() > n) {
        coeffs[id] = false;
      } else {
        coeffs[id] = true;

        matrix = matrixTemp;
      }
    }

    int fibSeq = 0;
    for (id = 0; id < 7; id++) {
      if (coeffs[id]) {
        fibSeq += 1 << id;
      }
    }

    int reqNum = 0;
    switch ((fibSeq + 2) % 3) {
      case 0: {
        reqNum = fibSeq + 2;
        break;
      }
      case 1: {
        reqNum = fibSeq + 1;
        break;
      }

      case 2: {
        reqNum = fibSeq;
        break;
      }
    }

//    System.out.println("reqNum: " + reqNum);
    matrix = TwoTwoMatrix.identity();
    id = 0;
    while (reqNum > 0) {
      if (reqNum % 2 == 1) {
        matrix.multiply(powerCache.get(id));
      }
      reqNum >>= 1;
      id++;
    }

//    System.out.printf("Found: %d using %d\n", (matrix.getFib() - 1) / 2, matrix.getFib());
    return ((matrix.getFib()) - 1) / 2;
  }


  public static void main(String[] argc) {
    Scanner scanner = new Scanner(System.in);
    Eueler002Solver solver = new Eueler002Solver();

    for (int t = scanner.nextInt(); t > 0; t--) {
      System.out.println(solver.find(scanner.nextLong()));
    }
  }

}
