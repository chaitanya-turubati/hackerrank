package io.chatty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Chaitanya S. Turubati on 3/8/17.
 */
public class ZionMachineMatrixSolver {

  private int N;
  private int K;

  List<Node> nodes;

  Set<Integer> machines;

  Set<Edge> cleanedEdges;

  Map<Integer, Node> cleanedNodes;

  Map<Edge, Boolean> removedFlags = new HashMap<>();

  private ZionMachineMatrixSolver(int N, int K) {
    this.N = N;
    this.K = K;

    nodes = new ArrayList<>();
    for (int i = 0; i < N; ++i) {
      nodes.add(new Node(i));
    }

    machines = new HashSet<>();
    cleanedEdges = new HashSet<>();
  }

  private class Edge {

    private Integer a;
    private Integer b;
    private Integer cost;

    private Integer otherNode(Integer id) {
      if (id.equals(a)) {
        return b;
      } else if (id.equals(b)) {
        return a;
      } else {
        throw new RuntimeException();
      }
    }
  }

  private class Node {

    private Integer id;
    private Set<Edge> edges;

    Node(Integer id) {
      this.id = id;
      this.edges = new HashSet<>();
    }
  }

  private void addEdge(Integer a, Integer b, Integer cost) {

//    cost = 1 + (Math.abs(random.nextInt()) % 10);

    Node nodeA = nodes.get(a);
    Node nodeB = nodes.get(b);

    Edge edge = new Edge();
    edge.a = a;
    edge.b = b;
    edge.cost = cost;

    nodeA.edges.add(edge);
    nodeB.edges.add(edge);

    cleanedEdges.add(edge);
  }

  private void cleanUp() {
    Node node = nodes.get(machines.iterator().next());
    hasMachine(node, null);

//    for (Edge edge : cleanedEdges) {
//      System.out.printf("%d %d %d\n", edge.a, edge.b, edge.cost);
//    }
  }

  private int solve() {
    cleanUp();
    List<Edge> sortedEdges = new ArrayList<>();
    sortedEdges.addAll(cleanedEdges);
    Collections.sort(sortedEdges, new EdgeComparator());

    cleanedNodes = new HashMap<>();
    for (Edge edge : sortedEdges) {
      removedFlags.put(edge, false);

      cleanedNodes.putIfAbsent(edge.a, new Node(edge.a));
      cleanedNodes.putIfAbsent(edge.b, new Node(edge.b));

      cleanedNodes.get(edge.a).edges.add(edge);
      cleanedNodes.get(edge.b).edges.add(edge);
    }

    int removeCount = 0;

    for (Edge edge : sortedEdges) {
      if (removedFlags.get(edge)) {
        continue;
      }

      removeCount += edge.cost;
      removedFlags.put(edge, true);

      Node nodeA = cleanedNodes.get(edge.a);
      Node nodeB = cleanedNodes.get(edge.b);

      nodeA.edges.remove(edge);
      nodeB.edges.remove(edge);

//      System.out.printf("Removing %d %d %d\n", edge.a, edge.b, edge.cost);

      removeEdges(nodeA);
      removeEdges(nodeB);
    }

    return removeCount;
  }

  private void removeEdges(Node node) {
    if (node.edges.size() == 1 && !machines.contains(node.id)) {
      Edge edge = node.edges.iterator().next();
      removedFlags.put(edge, true);
//      System.out.printf("Ignoring %d %d %d\n", edge.a, edge.b, edge.cost);

      Node otherNode = cleanedNodes.get(edge.otherNode(node.id));

      otherNode.edges.remove(edge);

      removeEdges(otherNode);
    }
  }

  public static class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge o1, Edge o2) {
      return o1.cost.compareTo(o2.cost);
    }

  }

  private boolean hasMachine(Node node, Edge edge) {
    // reached the leaf node.
    if (node.edges.size() == 1 && node.edges.iterator().next().equals(edge)) {
      if (machines.contains(node.id)) {
        return true;
      } else {
        cleanedEdges.remove(edge);
        return false;
      }
    } else {
      boolean hasMachineBelow = false;
      for (Edge belowEdge : node.edges) {
        if (!belowEdge.equals(edge)) {
          Node other = nodes.get(belowEdge.otherNode(node.id));
          hasMachineBelow = hasMachineBelow | hasMachine(other, belowEdge);
        }
      }

      if (machines.contains(node.id)) {
        return true;
      } else {

        if (!hasMachineBelow) {
          cleanedEdges.remove(edge);
        }
        return hasMachineBelow;
      }
    }
  }

  public static void main(String[] argc) {
    Scanner scanner = new Scanner(System.in);

    ZionMachineMatrixSolver solver = new ZionMachineMatrixSolver(
        scanner.nextInt(), scanner.nextInt());

    for (int i = 0; i < solver.N - 1; ++i) {
      solver.addEdge(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
    }

    for (int i = 0; i < solver.K; ++i) {
      solver.machines.add(scanner.nextInt());
    }

    System.out.println(solver.solve());
  }

}
