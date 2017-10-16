// Copyright 2017 Lytcone

package io.chatty.big_sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BigSortingMain {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int N = scanner.nextInt();

    List<String> bigIntegers = new ArrayList<String>();

    int[] first100Count = new int[100];

    for (int i = 0; i < N; ++i) {
      String scannedValue = scanner.next();
      if (scannedValue.length() < 3) {
        first100Count[Integer.parseInt(scannedValue)]++;
      } else {
        bigIntegers.add(scannedValue);
      }
    }

    Collections.sort(bigIntegers, BigIntComparator);

    for (int i = 1; i < 100; ++i) {
      for (int j = 0; j < first100Count[i]; ++j) {
        System.out.println(i);
      }
    }

    Iterator<String> bigIntegerIterator = bigIntegers.iterator();
    while (bigIntegerIterator.hasNext()) {
      System.out.println(bigIntegerIterator.next());
    }
  }

  private static Comparator<String> BigIntComparator = new Comparator<String>() {
    public int compare(String lhs, String rhs) {
      int lengthDiff = lhs.length() - rhs.length();

      if (lengthDiff == 0) {
        return lhs.compareTo(rhs);
      } else {
        return lengthDiff;
      }
    }
  };
}
