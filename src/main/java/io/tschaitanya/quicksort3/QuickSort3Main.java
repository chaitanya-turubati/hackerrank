// Copyright 2017 Lytcone

package io.tschaitanya.quicksort3;

import java.util.Scanner;

public class QuickSort3Main {

  public int N;
  public int[] numbers;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    QuickSort3Main main = new QuickSort3Main();

    main.N = scanner.nextInt();
    main.numbers = new int[main.N];

    for (int i = 0; i < main.N; ++i) {
      main.numbers[i] = scanner.nextInt();
    }

    main.quickSort(0, main.N - 1);
  }

  private void quickSort(int lo, int hi) {
    if (lo < hi) {
      int p = partition(lo, hi);

      quickSort(lo, p - 1);
      quickSort(p + 1, hi);
    }
  }

  private void printState() {
    for (int i = 0; i < N; ++i) {
      System.out.print(numbers[i] + " ");
    }

    System.out.print("\n");
  }


  private int partition(int lo, int hi) {
    int pivot = numbers[hi];

    int i = lo - 1;

    for (int j = lo; j < hi; ++j) {
      if (numbers[j] <= pivot) {
        i++;
        if (i != j) {
          int temp = numbers[i];
          numbers[i] = numbers[j];
          numbers[j] = temp;
        }
      }
    }

    int temp = numbers[i + 1];
    numbers[i + 1] = numbers[hi];
    numbers[hi] = temp;

//    System.out.print(pivot + ": " + lo + "," + hi + ": ");
    printState();

    return i + 1;
  }
}
