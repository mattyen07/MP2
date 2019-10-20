package cpen221.mp2.graph;

import java.util.*;

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {

    private Map<V, Set<E>> graph = new HashMap<>();

    public Graph() {

    }

    public Graph(HashMap<V,Set<E>> data) {
        for (V v: data.keySet()) {
            Set<E> vertexInfo = new HashSet<>();
            vertexInfo.addAll(data.get(v));
            graph.put(v,vertexInfo);
        }
    }


    @Override
    public boolean addVertex(V v) {
        Set<E> set = new HashSet<>();

        this.graph.put(v, set);

        return this.graph.containsKey(v);
    }

    @Override
    public boolean vertex(V v) {
        return this.graph.containsKey(v);
    }

    @Override
    public boolean addEdge(E e) {
        return false;
    }

    @Override
    public boolean edge(E e) {
        return false;
    }

    @Override
    public boolean edge(V v1, V v2) {
        return false;
    }

    @Override
    public int edgeLength(V v1, V v2) {
        return 0;
    }

    @Override
    public int edgeLengthSum() {
        return 0;
    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public boolean remove(V v) {
        return false;
    }

    @Override
    public Set<V> allVertices() {
        return null;
    }

    @Override
    public Set<E> allEdges(V v) {
        return null;
    }

    @Override
    public Set<E> allEdges() {
        return null;
    }

    @Override
    public Map<V, E> getNeighbours(V v) {
        return null;
    }

    @Override
    public List<V> shortestPath(V source, V sink) {
        return null;
    }

    @Override
    public List<E> minimumSpanningTree() {
        return null;
    }

    @Override
    public int pathLength(List<V> path) {
        return 0;
    }

    @Override
    public Set<V> search(V v, int range) {
        return null;
    }

    @Override
    public int diameter() {
        return 0;
    }

    @Override
    public E getEdge(V v1, V v2) {
        return null;
    }

    //// add all new code above this line ////

    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement allVertices() and allEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the cpen221.mp2.graph.Edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.allVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.allEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            remove(trim);
        }
    }
}

