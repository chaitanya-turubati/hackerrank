package io.chatty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 17/8/17.
 */
public class TrainTripSolver {

  private static Scanner scanner = new Scanner(System.in);

  private static final int NumHackers = 3;

  private Map<Integer, Integer> initVertices;

  private List<Map<Integer, Integer>> adjacencyList;

  Long bestCost1, bestCost2, bestCost3;

  private void parseInput() {
    int n = scanner.nextInt();

    List<Long> groupCosts = new ArrayList<>();
    initVertices = new HashMap<>();

    adjacencyList = new ArrayList<>();
    for (int i = 0; i < n; ++i) {
      adjacencyList.add(new HashMap<>());
    }

    for (int i = 0; i < NumHackers; ++i) {
      groupCosts.add(scanner.nextLong());
    }

    for (int i = 0; i < NumHackers; ++i) {
      Integer node = scanner.nextInt() - 1;
      initVertices.put(node, initVertices.getOrDefault(node, 0) + 1);
    }

    for (int i = 0; i < n - 1; ++i) {
      Integer u = scanner.nextInt() - 1;
      Integer v = scanner.nextInt() - 1;

      adjacencyList.get(u).put(v, 0);
      adjacencyList.get(v).put(u, 0);
    }

    // Hardcoded for the case of 3;
    bestCost1 = groupCosts.get(0);
    bestCost2 = Math.min(groupCosts.get(1), groupCosts.get(0) * 2);
    bestCost3 = Math.min(groupCosts.get(2),
        Math.min(groupCosts.get(0) * 3, groupCosts.get(1) + groupCosts.get(0)));
  }

  private void createCounts(Integer node, Integer parent) {
    Integer count = 0;

    for (Integer child : adjacencyList.get(node).keySet()) {
      if (child.equals(parent)) {
        continue;
      }

      createCounts(child, node);

      count += adjacencyList.get(node).get(child);
    }

    if (initVertices.containsKey(node)) {
      count += initVertices.get(node);
    }

    if (parent != null) {
      adjacencyList.get(parent).put(node, count);

      System.out.printf("%d-%d: %d\n", parent, node, count);
    }
  }

  private NodeInfo traverse(Integer node, Integer parent) {

    List<NodeInfo> childInfos = new ArrayList<>();
    for (Integer child : adjacencyList.get(node).keySet()) {
      if (child.equals(parent) || adjacencyList.get(node).get(child) == 0) {
        continue;
      }

      childInfos.add(traverse(child, node));
//      System.out.printf("Node(%d) Cost: %d for %d nodes\n", child, childInfo.cost, childInfo.numInits);
    }

    NodeInfo info = new NodeInfo();
    if (parent == null) {
      info.count = 3;
    } else {
      info.count = adjacencyList.get(parent).get(node);
    }

    NodeInfo nodeB, nodeC = null, nodeD = null;
    if (childInfos.size() == 0) {
      info.cost0 = info.cost1 = info.cost2 = 0L;
      return info;
    } else if (childInfos.size() == 1) {
      nodeB = childInfos.get(0);
    } else if (childInfos.size() == 2) {
      if (childInfos.get(0).count == 1) {
        nodeB = childInfos.get(1);
        nodeC = childInfos.get(0);
      } else {
        nodeB = childInfos.get(0);
        nodeC = childInfos.get(1);
      }
    } else {
      nodeB = childInfos.get(0);
      nodeC = childInfos.get(1);
      nodeD = childInfos.get(2);
    }

    if (info.count == 3) {

      if (childInfos.size() == 1) {
        info.cost0 = nodeB.cost0 + bestCost3;
      } else if (childInfos.size() == 2) {
        info.cost0 = nodeB.cost0 + nodeC.cost0 + bestCost1 + bestCost2;
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost1 + 2 * bestCost2 + bestCost1);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost2 + 2 * bestCost2 + bestCost3);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost1 + nodeC.cost0 + 2 * bestCost1 + bestCost3);
      } else {
        info.cost0 = nodeB.cost0 + nodeC.cost0 + nodeD.cost0 + 3 * bestCost1;
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost0 + nodeD.cost1 + 3 * bestCost1 + bestCost2);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost1 + nodeD.cost0 + 3 * bestCost1 + bestCost2);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost1 + nodeC.cost0 + nodeD.cost0 + 3 * bestCost1 + bestCost2);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost0 + nodeD.cost2 + bestCost3 + bestCost2 + 2 * bestCost1);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost2 + nodeD.cost0 + bestCost3 + bestCost2 + 2 * bestCost1);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost2 + nodeC.cost0 + nodeD.cost0 + bestCost3 + bestCost2 + 2 * bestCost1);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost1 + nodeD.cost1 + 3 * bestCost1 + 2 * bestCost2);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost1 + nodeC.cost0 + nodeD.cost1 + 3 * bestCost1 + 2 * bestCost2);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost1 + nodeC.cost1 + nodeD.cost0 + 3 * bestCost1 + 2 * bestCost2);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost1 + nodeD.cost2 + 2 * bestCost1 + bestCost3);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost2 + nodeD.cost1 + 2 * bestCost1 + bestCost3);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost1 + nodeC.cost0 + nodeD.cost2 + 2 * bestCost1 + bestCost3);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost1 + nodeC.cost2 + nodeD.cost1 + 2 * bestCost1 + bestCost3);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost2 + nodeC.cost1 + nodeD.cost0 + 2 * bestCost1 + bestCost3);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost2 + nodeC.cost0 + nodeD.cost1 + 2 * bestCost1 + bestCost3);
      }

    } else if (info.count == 2) {
      if (childInfos.size() == 1) {
        info.cost0 = nodeB.cost0 + bestCost2;

        info.cost1 = nodeB.cost1 + bestCost1 + bestCost3;
        info.cost1 = Math.min(info.cost0, info.cost1);
      } else {
        info.cost0 = nodeB.cost0 + nodeC.cost0 + 2 * bestCost1;
        info.cost0 = Math.min(info.cost0,
            nodeB.cost0 + nodeC.cost1 + 2 * bestCost1 + bestCost2);
        info.cost0 = Math.min(info.cost0,
            nodeB.cost1 + nodeC.cost0 + 2 * bestCost1 + bestCost2);

        info.cost1 = Math.min(
            nodeB.cost0 + nodeC.cost2 + 2 * bestCost1 + bestCost3,
            nodeB.cost2 + nodeC.cost0 + 2 * bestCost1 + bestCost3);
        info.cost1 = Math.min(info.cost0, info.cost1);
      }
    } else {
      info.cost0 = nodeB.cost0 + bestCost1;

      info.cost1 = nodeB.cost1 + bestCost1 + bestCost2;
      info.cost1 = Math.min(info.cost1, info.cost0);

      info.cost2 = nodeB.cost2 + bestCost2 + bestCost3;
      info.cost2 = Math.min(info.cost1, info.cost2);
    }

    return info;
  }

  private NodeInfo traverse(Integer rootNode) {
    createCounts(rootNode, null);
    return traverse(rootNode, null);
  }

  private static class NodeInfo {

    Long cost0 = null;
    Long cost1 = null;
    Long cost2 = null;
    public Integer count = 0;
  }

  public static void main(String[] args) {
    for (int t = scanner.nextInt(); t > 0; --t) {
      TrainTripSolver solver = new TrainTripSolver();
      solver.parseInput();
      System.out.println(solver.traverse(0).cost0);
    }
  }

}
