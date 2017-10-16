package io.chatty;

import java.util.Scanner;

public class TFFDU {
  public static class Solution {
    public int solution(String S) {
      return S.length();
    }
  }

  public static void main(String[] args) {
    System.out.println((new Solution()).solution((new Scanner(System.in)).next()));
  }

}
