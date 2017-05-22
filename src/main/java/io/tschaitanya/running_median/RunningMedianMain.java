package io.tschaitanya.running_median;

import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class RunningMedianMain {

  private Integer median = null;

  private TreeMap<Integer, Integer> greaterElements = new TreeMap<Integer, Integer>();
  private TreeMap<Integer, Integer> lesserElements = new TreeMap<Integer, Integer>();

  private int greaterSize = 0;
  private int lesserSize = 0;

  private boolean isOdd() {
    return greaterSize == lesserSize;
  }

  private void addToGreater(Integer elem) {
    greaterSize++;
    Integer currSize = greaterElements.get(elem);
    greaterElements.put(elem, currSize == null ? 1 : (currSize + 1));
  }

  private void addToLesser(Integer elem) {
    lesserSize++;
    Integer currSize = lesserElements.get(elem);
    lesserElements.put(elem, currSize == null ? 1 : (currSize + 1));
  }

  private void pollFromLesser() {
    Entry<Integer, Integer> lastEntry = lesserElements.lastEntry();
    if (lastEntry.getValue() == 1) {
      lesserElements.remove(lastEntry.getKey());
    } else {
      lesserElements.put(lastEntry.getKey(), lastEntry.getValue() - 1);
    }

    lesserSize--;

    median = lastEntry.getKey();
  }

  private void pollFromGreater() {
    Entry<Integer, Integer> firstEntry = greaterElements.firstEntry();
    if (firstEntry.getValue() == 1) {
      greaterElements.remove(firstEntry.getKey());
    } else {
      greaterElements.put(firstEntry.getKey(), firstEntry.getValue() - 1);
    }

    greaterSize--;

    median = firstEntry.getKey();
  }

  private void addElement(Integer elem) {
    if (median == null) {
      median = elem;
      return;
    }

    if (elem >= median) {
      if (isOdd()) {
        addToGreater(elem);
      } else {
        addToGreater(elem);
        addToLesser(median);
        pollFromGreater();
      }
    } else {
      if (isOdd()) {
        addToLesser(elem);
        addToGreater(median);
        pollFromLesser();
      } else {
        addToLesser(elem);
      }
    }
  }

  private Double getMedian() {
    if (isOdd()) {
      return Double.valueOf(median);
    } else {
      return (median + greaterElements.firstKey()) / 2.0;
    }
  }

  private void printStuff() {
//    System.out.print(sortedList);
    System.out.println(getMedian());
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    RunningMedianMain main = new RunningMedianMain();
    int T = scanner.nextInt();
    for (int t = 0; t < T; t++) {

      if (t == 367) {
        t = t + 1 - 1;
      }

      main.addElement(scanner.nextInt());
      main.printStuff();
    }
  }
}
