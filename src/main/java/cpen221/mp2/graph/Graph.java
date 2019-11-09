package cpen221.mp2.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Deque;
import java.util.LinkedList;


/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {
    private Map<V, Set<E>> graph = new HashMap<>();

    /*
    Abstraction Function: Creates an undirected graph of vertices and connecting edges.
     */

    /*
    Rep Invariant: if one can travel vertex v1 to vertex v2 along edge e,
    one can travel from v2 to v1 along that same edge.
    Edge.length > 0.
     */


    /**
     * Constructs an empty graph object.
     */
    public Graph() {
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
     *
     * @param v is not null and is a vertex that has already been added.
     * @param e is not null.
     */
    private void addEdgeToVertex(V v, E e) {
        Set<E> vertexData = this.graph.get(v);
        vertexData.add(e);
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graph and false otherwise
     */
    @Override
    public boolean edge(E e) {
        Set<V> vertexSet = this.graph.keySet();

        for (V vertex : vertexSet) {
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

            for (E edge : edgeSet) {
                V vertex1 = edge.v1();
                V vertex2 = edge.v2();

                if (vertex1.equals(v1) && vertex2.equals(v2)) {
                    return true;
                } else if (vertex1.equals(v2) && vertex2.equals(v1)) {
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

        for (E edge : edgeSet) {
            if (edge.v1().equals(v1) && edge.v2().equals(v2)) {
                return edge.length();
            } else if (edge.v1().equals(v2) && edge.v2().equals(v1)) {
                return edge.length();
            }
        }

        return 0;
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     * returns 0 if no edges are in the graph
     */
    @Override
    public int edgeLengthSum() {
        int sum = 0;
        Set<E> edgeSet = new HashSet<>();

        for (V v : this.graph.keySet()) {
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

        for (V v : this.graph.keySet()) {
            Set<E> edgeSet = this.graph.get(v);
            if (edgeSet.contains(e)) {
                edgeSet.remove(e);
                removalFlag = true;
            }
            this.graph.replace(v, edgeSet);
        }

        return !this.edge(e) && removalFlag;
    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed from the graph and false otherwise
     */
    @Override
    public boolean remove(V v) {
        boolean removalFlag = false;

        if (this.graph.containsKey(v)) {
            this.graph.remove(v);
            removalFlag = true;
        }

        return !this.graph.containsKey(v) && removalFlag;
    }

    /**
     * Obtain a set of all vertices in the graph.
     *
     * @return a set of all vertices in the graph
     * returns an empty set if no vertices are in the graph
     */
    @Override
    public Set<V> allVertices() {
        return this.graph.keySet();
    }

    /**
     * Obtain a set of all vertices incident on v.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     * returns an empty set if no edges are incident to v
     */
    @Override
    public Set<E> allEdges(V v) {
        return this.graph.get(v);
    }

    /**
     * Obtain a set of all edges in the graph.
     *
     * @return all edges in the graph
     * returns an empty set if no edges are in the graph
     */
    @Override
    public Set<E> allEdges() {
        Set<E> edgeSet = new HashSet<>();

        for (V vertex : this.graph.keySet()) {
            Set<E> edges = this.graph.get(vertex);

            for (E edge : edges) {
                if (!edgeSet.contains(edge)) {
                    edgeSet.add(edge);
                }
            }
        }

        return edgeSet;
    }

    /**
     * Obtain all the neighbours of vertex v.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    @Override
    public Map<V, E> getNeighbours(V v) {
        Map<V, E> neighbours = new HashMap<>();

        for (E e : this.graph.get(v)) {
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
     * Computes the shortest path from source to sink
     * Sources: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     *          https://www.youtube.com/watch?v=gdmfOwyQlcI
     *          https://www.youtube.com/watch?v=pVfj6mxhdMw
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink
     * (both end points are part of the list)
     * returns an empty list if there is no path from source to sink
     * If source and sink are the same, returns a list of length 1 containing only the source/sink.
     *
     */
    @Override
    public List<V> shortestPath(V source, V sink) {
        //initialize set of visited vertex's
        Set<V> visited = new HashSet<>();


        //get set of all vertexes in graph
        Set<V> vertexes = this.allVertices();


        //get weights of shortest paths. Set all initial weights to Integer.MAX_VALUE
        //set source weight to 0.
        Map<V, Integer> weights = new HashMap<>();
        for (V v : vertexes) {
            weights.put(v, Integer.MAX_VALUE);
        }
        weights.replace(source, 0);

        //this map keeps track of the previous vertex that gives the shortest path to the sink.
        Map<V, V> previousVertex = new HashMap<>();

        V currentVertex = source;
        previousVertex.put(source, source);

        Map<V, E> unvisitedNeighbours;

        while (!visited.contains(sink)) {

            int currentDistance = weights.get(currentVertex);
            visited.add(currentVertex);


            //get neighbours
            unvisitedNeighbours = this.getNeighbours(currentVertex);
            //remove visited neighbours
            for (V v : visited) {
                if (unvisitedNeighbours.containsKey(v)) {
                    unvisitedNeighbours.remove(v);
                }
            }

            //update weights map and if necessary update previousVertex map.
            for (V v : unvisitedNeighbours.keySet()) {
                if (unvisitedNeighbours.get(v).length() + currentDistance <= weights.get(v)) {
                    weights.replace(v, unvisitedNeighbours.get(v).length() + currentDistance);
                    previousVertex.put(v, currentVertex);
                }
            }


            //set current vertex to closest overall unvisited vertex
            int closestDistance = Integer.MAX_VALUE;
            for (V v : vertexes) {
                if (!visited.contains(v) && weights.get(v) <= closestDistance) {
                    currentVertex = v;
                    closestDistance = weights.get(v);
                }
            }

            //if there is no path between source and sink.
            if (closestDistance == Integer.MAX_VALUE && !visited.contains(sink)) {
                return new ArrayList<>();
            }
        }

        return generatePath(previousVertex, sink, source);
    }

    /**
     * Helper method for shortestPath to generate the list of vertices on the shortest path
     * @param parentVertices  A hash map with information on the previous vertex. The key vertex maps
     *                        to the vertex before it on the shortest paht.
     * @param sink the end vertex
     * @param source the start vertex
     * @return a list of vertices from source to sink on the shortest path
     */

    private List<V> generatePath(Map<V, V> parentVertices, V sink, V source) {
        List<V> shortestPath = new ArrayList<>();
        V vertex = sink;
        shortestPath.add(vertex);

        while (!vertex.equals(source)) {
            vertex = parentVertices.get(vertex);
            shortestPath.add(vertex);
        }

        List<V> pathSourceToSink = new ArrayList<>();

        for (int i = shortestPath.size() - 1; i >= 0; i--) {
            pathSourceToSink.add(shortestPath.get(i));
        }

        return pathSourceToSink;
    }


    /**
     * Compute the minimum spanning tree of the graph.
     * See https://en.wikipedia.org/wiki/Minimum_spanning_tree
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     * if there is no MST, returns an empty list
     */
    @Override
    public List<E> minimumSpanningTree() {
        ArrayList<E> allEdges = new ArrayList<>();
        Set<V> vertexSet = this.allVertices();
        List<E> mstList = new ArrayList<>();
        Set<Set<V>> vertexMerge = new HashSet<>();

        //adds all edges into a list
        for (V v : vertexSet) {
            Set<E> edgeSet = this.graph.get(v);

            for (E edge : edgeSet) {
                if (!allEdges.contains(edge)) {
                    allEdges.add(edge);
                }
            }
            //puts each vertex into its own set such that we can merge the sets after
            Set<V> vertexSeen = new HashSet<>();
            vertexSeen.add(v);
            vertexMerge.add(vertexSeen);
        }

        //All MST's will have a size equal to the # of vertices - 1
        while (mstList.size() < vertexSet.size() - 1) {
            boolean merge = true;
            E shortestEdge = findShortestEdge(allEdges);
            V v1 = shortestEdge.v1();
            V v2 = shortestEdge.v2();

            //checks to see if the two edge vertices are already in a set together
            for (Set<V> vSet : vertexMerge) {
                if (vSet.contains(v1) && vSet.contains(v2)) {
                    merge = false;
                    break;
                }
            }

            //if the two vertices on the edge are not in the same set, merge the sets together
            //and add the edge to our MST
            if (merge) {
                mstList.add(shortestEdge);
                mergeSets(vertexMerge, shortestEdge);
            }

            allEdges.remove(shortestEdge);

            //if we don't exit the loop and we have no more edges, there is no MST
            if (allEdges.isEmpty()) {
                return new ArrayList<>();
            }
        }

        return mstList;
    }

    /**
     * Helper method for MST, finds the shortest edge given a list of edges
     * @param edgeSet has at least 1 element
     * @return the shortest length edge in the given edgeSet
     */
    private E findShortestEdge(List<E> edgeSet) {
        E shortestEdge = edgeSet.get(0);
        int minLength = Integer.MAX_VALUE;

        for (E edge : edgeSet) {
            if (edge.length() < minLength) {
                minLength = edge.length();
                shortestEdge = edge;
            }
        }
        return shortestEdge;
    }

    /**
     * Helper method for MST, merges two sets within the set of vertices together such that we
     * can identify cycles in the MST
     * @param vertexMerge a set of a set of vertices in the graph
     * @param shortestEdge is an edge in the graph
     */

    private void mergeSets(Set<Set<V>> vertexMerge, E shortestEdge) {
        Set<V> v1Set = new HashSet<>();
        Set<V> v2Set = new HashSet<>();

        //find the sets that contain v1 and v2 respectively
        for (Set<V> vSet: vertexMerge) {
            if (vSet.contains(shortestEdge.v1())) {
                v1Set = vSet;
            }
            if (vSet.contains(shortestEdge.v2())) {
                v2Set = vSet;
            }
        }
            v1Set.addAll(v2Set);
            vertexMerge.remove(v2Set);
    }

    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path
     * @return the length of given path of vertices,
     * If the list length is 1 then we return 0 as we are at the same vertex
     * If the list is empty, we return 0 to indicate there is no path length
     */
    @Override
    public int pathLength(List<V> path) {
        int sum = 0;

        if (path.size() <= 1) {
            return 0;
        }

        for (int i = 1; i < path.size(); i++) {
            V v1 = path.get(i - 1);
            V v2 = path.get(i);

            int length = this.getEdge(v1, v2).length();

            sum += length;
        }

        return sum;
    }

    /**
     * Obtain all vertices w that are no more than a path distance of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @return a set of vertices that are within range of v (this set does not contain v).
     * returns an empty set if no vertices are within range of v
     */
    @Override
    public Set<V> search(V v, int range) {
        Set<V> vertexSet = this.graph.keySet();
        Set<V> vertexInRange = new HashSet<>();
        int pathLength;

        for (V vertex : vertexSet) {

            if (!vertex.equals(v)) {
                List<V> shortestPath = shortestPath(v, vertex);

                if (shortestPath.equals(Collections.emptyList())) {
                    pathLength = Integer.MAX_VALUE;
                } else {
                    pathLength = pathLength(shortestPath);
                }

                if (pathLength < range) {
                    vertexInRange.add(vertex);
                }
            }
        }

        return vertexInRange;
    }

    /**
     * Compute the diameter of the graph.
     *
     * The diameter of a graph is the length of the longest shortest path in the graph.
     * If a graph has multiple components then we will define the diameter
     * as the diameter of the largest component.
     *
     * @return the diameter of the graph.
     * If there are two largest components of a graph, returns the largest diameter of the
     * the two largest components
     */
    @Override
    public int diameter() {
        Set<V> largestComponent = this.getLargestComponent();
        return diameterOfComponent(largestComponent);
    }

    /**
     * Returns a set of all vertices contained in largest component of graph.
     * If there are two components of equal amounts of vertices, returns the component
     * with the largest diameter.
     * @return set of vertices
     */
    private Set<V> getLargestComponent() {
        HashMap<V, Set<V>> componentMap = new HashMap<>();

        for (V v1 : this.graph.keySet()) {
            Set<V> vertexSet = new HashSet<>();
            componentMap.put(v1, vertexSet);
            for (V v2 : this.graph.keySet()) {
                List<V> path = shortestPath(v1, v2);
                if (!path.isEmpty()) {
                    vertexSet.add(v2);
                }
            }
        }

        int largestComponentSize = 0;
        Set<V> largestComponentSet = new HashSet<>();

        for (V vertex : componentMap.keySet()) {
            int currentComponentSize = componentMap.get(vertex).size();

            if (currentComponentSize > largestComponentSize) {

                largestComponentSize = componentMap.get(vertex).size();
                largestComponentSet = componentMap.get(vertex);

            } else if (currentComponentSize == largestComponentSize
                    && diameterOfComponent(componentMap.get(vertex))
                    > diameterOfComponent(largestComponentSet)) {

                largestComponentSet = componentMap.get(vertex);

            }
        }

        return largestComponentSet;
    }

    /**
     * returns diameter of Component. Diameter of the component is defined as the length
     * of the longest shortest path.
     * @param componentVectors all vectors part of one graph component
     * @return diameter of component.
     */
    private int diameterOfComponent(Set<V> componentVectors) {
        int longestPath = 0;
        int length;

        for (V v1 : componentVectors) {
            for (V v2 : componentVectors) {
                length = pathLength(shortestPath(v1, v2));
                if (length > longestPath) {
                    longestPath = length;
                }
            }
        }

        return longestPath;
    }




    /**
     * Find the edge that connects two vertices if such an edge exists.
     * Note: Changes to returned edge E may mutate graph.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2, returns null if the edge doesn't exist in the graph
     */
    @Override
    public E getEdge(V v1, V v2) {
        Edge<Vertex> edgeCheck = new Edge<>(v1, v2);

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

