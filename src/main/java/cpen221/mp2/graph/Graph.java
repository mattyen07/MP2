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

    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false otherwise
     */
    @Override
    public boolean addVertex(V v) {
        Set<E> set = new HashSet<>();
        Set<V> vertexSet = this.graph.keySet();

        for (V vertex : vertexSet) {
            if (v.id() == (vertex.id())) {
                return false;
            }
        }

         this.graph.put(v, set);

        return this.graph.containsKey(v);
    }

    /**
     * Check if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    @Override
    public boolean vertex(V v) {
        return this.graph.containsKey(v);
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added and false otherwise
     */
    @Override
    public boolean addEdge(E e) {
        V v1 = e.v1();
        V v2 = e.v2();

        Set<E> vertexSet1 = this.graph.get(v1);
        Set<E> vertexSet2 = this.graph.get(v2);

        if (vertexSet1.contains(e) || vertexSet2.contains(e)) {
            return false;
        } else {
            addEdgeToVertex(v1, e);
            addEdgeToVertex(v2, e);
        }

        return true;
    }

    /**
     * adds edge e to vertex v.
     * @param v is not null and is a vertex that has already been added.
     * @param e is not null.
     */
    private void addEdgeToVertex(V v,E e) {
        Set<E> vertexData = this.graph.get(v);
        vertexData.add(e);
        this.graph.replace(v,vertexData);
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graoh and false otherwise
     */
    @Override
    public boolean edge(E e) {
        Set<V> vertexSet = this.graph.keySet();

        for(V vertex : vertexSet) {
            Set<E> edgeSet = this.graph.get(vertex);
            for (E edge : edgeSet) {
                if (edge.equals(e)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true of the v1-v2 edge is part of the graph and false otherwise
     */
    @Override
    public boolean edge(V v1, V v2) {
        Set<V> vertexSet = this.graph.keySet();

        for (V vertex : vertexSet) {
            Set<E> edgeSet = this.graph.get(vertex);
            for (E edge: edgeSet) {
                V vertex1 = edge.v1();
                V vertex2 = edge.v2();

                if (vertex1.equals(v1) && vertex2.equals(v2)) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 edge if this edge is part of the graph,
     * returns 0 if edge is not found.
     */
    @Override
    public int edgeLength(V v1, V v2) {

        Set<E> edgeSet = this.graph.get(v1);

        for(E edge : edgeSet) {
            if (edge.v1().equals(v1) && edge.v2().equals(v2)) {
                return edge.length();
            }
        }

        return 0;
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    @Override
    public int edgeLengthSum() {
        int sum = 0;
        Set<E> edgeSet = new HashSet<>();
        for(V v : this.graph.keySet()) {
            for (E edge : this.graph.get(v)) {
                if (!edgeSet.contains(edge)) {
                    sum += edge.length();
                    edgeSet.add(edge);
                }
            }
        }

        return sum;
    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    @Override
    public boolean remove(E e) {
        boolean removalFlag = false;

        for (V v: graph.keySet()) {
            Set<E> edgeSet = graph.get(v);
            if(edgeSet.contains(e)) {
                edgeSet.remove(e);
                removalFlag = true;
            }
            graph.replace(v,edgeSet);
        }

        if (!edge(e) && removalFlag) {
            return true;
        }

        return false;
    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false otherwise
     */
    @Override
    public boolean remove(V v) {
        boolean removalFlag = false;
        if (graph.containsKey(v)) {
            graph.remove(v);
            removalFlag = true;
        }
        if (!graph.containsKey(v) && removalFlag) {
            return true;
        }
        return false;
    }

    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    @Override
    public Set<V> allVertices() {
        Set<V> vertexSet = new HashSet<>();

        for (V vertex : this.graph.keySet()) {
            int id = vertex.id();
            String name = vertex.name();
            Vertex newVert = new Vertex(id, name);
            V vertAdd = (V) newVert;
            vertexSet.add(vertAdd);
        }

        return vertexSet;
    }

    /**
     * Obtain a set of all vertices incident on v.
     * Access to this set **should not** permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    @Override
    public Set<E> allEdges(V v) {
        Set<E> edges = new HashSet<>();
        for(E e:graph.get(v)) {
            V v1 = e.v1();
            V v2 = e.v2();
            int Length = e.length();
            Edge edge = new Edge<V>(v1,v2,Length);
            E edgeImmutable = (E) edge;
            edges.add(edgeImmutable);
        }
        return edges;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return all edges in the graph
     */
    @Override
    public Set<E> allEdges() {
        Set<E> edgeSet = new HashSet<>();
        Set<E> edgeSeen = new HashSet<>();

        for (V vertex : this.graph.keySet()) {
            Set<E> edges = this.graph.get(vertex);
            for (E edge : edges) {
                if (!edgeSeen.contains(edge)) {
                    V v1 = edge.v1();
                    V v2 = edge.v2();
                    int length = edge.length();
                    Edge newEdge = new Edge(v1, v2, length);
                    E addEdge = (E) newEdge;
                    edgeSet.add(addEdge);
                    edgeSeen.add(addEdge);
                }
            }
        }

        return edgeSet;
    }

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map **should not** permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    @Override
    public Map<V, E> getNeighbours(V v) {

        Set<E> edgesOfv = allEdges(v);
        Map<V, E> neighbours = new HashMap<>();
        for (E e: edgesOfv) {
            V v1 = e.v1();
            V v2 = e.v2();
            if (!v1.equals(v)) {
                neighbours.put(v1, e);
            } else {
                neighbours.put(v2, e);
            }
        }

        return neighbours;
    }

    ///////////////Start of ImGraph Implementation/////////////////////

    /**
     * Compute the shortest path from source to sink
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink (both end points are part of the list)
     */
    @Override
    public List<V> shortestPath(V source, V sink) {
        return null;
    }

    /**
     * Compute the minimum spanning tree of the graph.
     * See https://en.wikipedia.org/wiki/Minimum_spanning_tree
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     */
    @Override
    public List<E> minimumSpanningTree() {
        ArrayList<E> allEdges = new ArrayList<>();
        Set<V> vertexSet = this.allVertices();
        List<E> MST = new ArrayList<>();
        int countE = this.graph.size() - 1;

        for (V v : vertexSet) { //adds all edges into a list
            Set<E> edgeSet = this.graph.get(v);
            for (E edge : edgeSet) {
                if (!allEdges.contains(edge)) {
                    allEdges.add(edge);
                }
            }
        }


        while (countE != 0) {
            E compareEdge = allEdges.get(0); //holder to add into MST list
            int minLength = Integer.MAX_VALUE;

            for (E edge : allEdges) {

                if (edge.length() < minLength && !MST.contains(edge)) {
                    compareEdge = edge;
                    minLength = edge.length();
                }

            }

            MST.add(compareEdge);

            countE--;
        }

        return MST;
    }

    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path
     * @return the length of path
     */
    @Override
    public int pathLength(List<V> path) {
        return 0;
    }

    /**
     * Obtain all vertices w that are no more than a <em>path distance</em> of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @return a set of vertices that are within range of v (this set does not contain v).
     */
    @Override
    public Set<V> search(V v, int range) {
        return null;
    }

    /**
     * Compute the diameter of the graph.
     * <ul>
     * <li>The diameter of a graph is the length of the longest shortest path in the graph.</li>
     * <li>If a graph has multiple components then we will define the diameter
     * as the diameter of the largest component.</li>
     * </ul>
     *
     * @return the diameter of the graph.
     */
    @Override
    public int diameter() {
        return 0;
    }

    /**
     * Find the edge that connects two vertices if such an edge exists.
     * This method should not permit graph mutations.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2, returns null if the edge doesn't exist in the graph
     */
    @Override
    public E getEdge(V v1, V v2) {
        Edge edgeCheck = new Edge (v1, v2);

        for (V v : this.graph.keySet()) {
            Set<E> edgeSet = this.graph.get(v);
            for (E edge : edgeSet) {
                if (edgeCheck.equals(edge)) {
                    return edge;
                }
            }
        }
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

