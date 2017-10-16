package io.chatty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by Chaitanya S. Turubati on 6/7/17.
 */
public class SuperMancunian {

  private PriorityQueue<Edge> edges;

  private UnionFind components;

  public SuperMancunian(Integer n) {
    this.components = new UnionFind(n);
    this.edges = new PriorityQueue<>();
  }

  public void addEdge(Integer nodeA, Integer nodeB, Integer cost) {
    this.edges.add(new Edge(nodeA - 1, nodeB - 1, cost));
  }

  public static class Edge implements Comparable<Edge> {

    Integer nodeA;
    Integer nodeB;

    Integer cost;

    public Edge(Integer nodeA, Integer nodeB, Integer cost) {
      this.nodeA = nodeA;
      this.nodeB = nodeB;
      this.cost = cost;
    }

    @Override
    public int compareTo(Edge other) {
      return this.cost.compareTo(other.cost);
    }
  }

  private void findComponents(Set<Edge> edges) {
    Map<Integer, Integer> levelNodess = new HashMap<>();
    for (Edge edge : edges) {
      levelNodess.put(components.find(edge.nodeA), 0);
      levelNodess.put(components.find(edge.nodeB), 0);
    }

  }

  private void runKrushkals() {
    // Next set of edges which are added greedily.
    Set<Edge> nextSet = new HashSet<>();
    Integer setEdgeCost = edges.peek().cost;
    while (edges.peek().cost.equals(setEdgeCost)) {
      nextSet.add(edges.poll());
    }

  }

  public static void main(String[] args) {
  }

  public static class UnionFind {

    private Integer[] parent;
    private Integer[] rank;

    private Integer count;

    public UnionFind(Integer count) {
      this.count = count;
      this.parent = new Integer[count];
      this.rank = new Integer[count];

      for (Integer i = 0; i < count; ++i) {
        this.parent[i] = i;
        this.rank[i] = 0;
      }
    }

    private Integer find(Integer x) {
      while (!x.equals(parent[x])) {
        parent[x] = parent[parent[x]];
        x = parent[x];
      }

      return x;
    }

    public Integer count() {
      return this.count;
    }

    public void union(Integer p, Integer q) {
      Integer rootP = find(p);
      Integer rootQ = find(q);

      if (rootP.equals(rootQ)) {
        return;
      }

      if (rank[rootP] < rank[rootQ]) {
        parent[rootP] = rootQ;
      } else if (rank[rootP] > rank[rootQ]) {
        parent[rootQ] = rootP;
      } else {
        parent[rootQ] = rootP;
        rank[rootP]++;
      }

      count--;
    }
  }

}
