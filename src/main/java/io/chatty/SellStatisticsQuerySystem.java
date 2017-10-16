package io.chatty;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 1/8/17.
 */
public class SellStatisticsQuerySystem {

  public class CountTable {

    int[][][][] pcsrCounts = new int[11][6][8][27];

    int[] pids = new int[2];
    int[] cids = new int[2];
    int[] sids = new int[2];
    int[] rids = new int[2];

    {
      pids[0] = 0;
      cids[0] = 0;
      sids[0] = 0;
      rids[0] = 0;
    }

    public CountTable() {
      for (int[][][] csrCounts : pcsrCounts) {
        for (int[][] srCounts : csrCounts) {
          for (int[] rCounts : srCounts) {
            Arrays.fill(rCounts, 0);
          }
        }
      }
    }


    void addSale(int productId, int categoryId, int stateId, int regionId) {
      pids[1] = productId;
      cids[1] = categoryId;
      sids[1] = stateId;
      rids[1] = regionId;

      for (int pid : pids) {
        for (int cid : cids) {
          for (int sid : sids) {
            for (int rid : rids) {
              pcsrCounts[pid][cid][sid][rid]++;
            }
          }
        }
      }
    }

    int get(int productId, int categoryId, int stateId, int regionId) {
      return pcsrCounts[productId][categoryId][stateId][regionId];
    }

  }

  private CountTable[] countTables = new CountTable[100];
  {
    for (int i = 0; i < 100; ++i) {
      countTables[i] = new CountTable();
    }
  }

  private void addSale(String saleStr) {
    String[] subStrs = saleStr.split(" ");

    int day = Integer.parseInt(subStrs[1]) - 1;

    String[] pcStr = subStrs[2].split("\\.");

    int productId = Integer.parseInt(pcStr[0]);
    int categoryId = 5;
    if (pcStr.length == 2) {
      categoryId = Integer.parseInt(pcStr[1]);
    }

    String[] srStr = subStrs[3].split("\\.");
    int stateId = Integer.parseInt(srStr[0]);
    int regionId = 26;
    if (srStr.length == 2) {
      regionId = Integer.parseInt(srStr[1]);
    }

    countTables[day].addSale(productId, categoryId, stateId, regionId);
  }

  private int query(String queryStr) {
    String[] subStrs = queryStr.split(" ");

    String[] dayStrs = subStrs[1].split("\\.");
    int dayStart = Integer.parseInt(dayStrs[0]) - 1;
    int dayEnd = dayStart;
    if (dayStrs.length == 2) {
      dayEnd = Integer.parseInt(dayStrs[1]) - 1;
    }

    String[] pcStr = subStrs[2].split("\\.");

    int productId = Integer.parseInt(pcStr[0]);
    productId = productId == -1 ? 0 : productId;
    int categoryId = 0;
    if (pcStr.length == 2) {
      categoryId = Integer.parseInt(pcStr[1]);
    }

    String[] srStr = subStrs[3].split("\\.");
    int stateId = Integer.parseInt(srStr[0]);
    stateId = stateId == -1 ? 0 : stateId;
    int regionId = 0;
    if (srStr.length == 2) {
      regionId = Integer.parseInt(srStr[1]);
    }

    int totalCount = 0;
    for (int d = dayStart; d <= dayEnd; d++) {
      totalCount += countTables[d].get(productId, categoryId, stateId, regionId);
    }
    return totalCount;
  }

  public static void main(String[] argc) {
    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();
    scanner.nextLine();
    SellStatisticsQuerySystem system = new SellStatisticsQuerySystem();
    for (int t = 0; t < T; ++t) {
      String str = scanner.nextLine();

      if (str.charAt(0) == 'Q') {
        System.out.println(system.query(str));
      } else if (str.charAt(0) == 'S') {
        system.addSale(str);
      }
    }
  }


}
