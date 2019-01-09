/*
* A collection of graph algorithms.
 */
package graphbase;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author DEI-ESINF
 */
public class GraphAlgorithms {

    /**
     * Performs breadth-first search of a Graph starting in a Vertex
     *
     * @param g Graph instance
     * @param vert information of the Vertex that will be the source of the
     * search
     * @return qbfs a queue with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {

        if (!g.validVertex(vert)) {
            return null;
        }

        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];  //default initializ.: false

        qbfs.add(vert);
        qaux.add(vert);
        int vKey = g.getKey(vert);
        visited[vKey] = true;

        while (!qaux.isEmpty()) {
            vert = qaux.remove();
            for (Edge<V, E> edge : g.outgoingEdges(vert)) {
                V vAdj = g.opposite(vert, edge);
                vKey = g.getKey(vAdj);
                if (!visited[vKey]) {
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                    visited[vKey] = true;
                }
            }
        }
        return qbfs;
    }

    /**
     * Performs depth-first search starting in a Vertex
     *
     * @param g Graph instance
     * @param vOrig Vertex of graph g that will be the source of the search
     * @param visited set of discovered vertices
     * @param qdfs queue with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> graph, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        visited[graph.getKey(vOrig)] = true;
        int i = -1;
        for (V vAdj : graph.vertices()) {
            i++;
            if (vOrig != vAdj) {
                if (graph.getEdge(vOrig, vAdj) != null && visited[i] == false) {
                    qdfs.add(vAdj);
                    DepthFirstSearch(graph, vAdj, visited, qdfs);
                }
            }
        }
    }

    /**
     * @param g Graph instance
     * @param vert information of the Vertex that will be the source of the
     * search
     * @return qdfs a queue with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {

        if (!g.validVertex(vert)) {
            return null;
        }
        boolean[] knownVertices = new boolean[g.numVertices()];
        LinkedList<V> resultQueue = new LinkedList<V>();
        resultQueue.add(vert);

        DepthFirstSearch(g, vert, knownVertices, resultQueue);
        return resultQueue;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param vDest Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path stack with vertices of the current path (the path is in
     * reverse order)
     * @param paths ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
            LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        int v1 = g.getKey(vOrig);

        visited[v1] = true;

        path.push(vOrig);

        for (Edge e : g.outgoingEdges(vOrig)) {
            V vAdj = (V) e.getVDest();
            if (vAdj.equals(vDest)) {
                path.push(vDest);
                LinkedList<V> listaAdicionar = (LinkedList<V>) path.clone();
                paths.add(listaAdicionar);
                path.pop();
            } else {
                if (!visited[g.getKey(vAdj)]) {
                    GraphAlgorithms.allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
        }

        visited[v1] = false;
        path.pop();
    }

    /**
     * @param g Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from voInf to vdInf
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return null;
        }
        boolean visited[] = new boolean[g.numVertices()];
        LinkedList<V> path = new LinkedList<>();
        ArrayList<LinkedList<V>> paths = new ArrayList<>();

        GraphAlgorithms.allPaths(g, vOrig, vDest, visited, path, paths);

        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with nonnegative edge weights This implementation
     * uses Dijkstra's algorithm
     *
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param visited set of discovered vertices
     * @param pathkeys minimum path vertices keys
     * @param dist minimum distances
     */
    private static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, V[] vertices,
            boolean[] visited, int[] pathKeys, double[] dist) {

        int i = g.getKey(vOrig);
        dist[i] = 0;
        while (i != -1) {

            vOrig = vertices[i];
            int kOrg = g.getKey(vOrig);
            visited[kOrg] = true;

            for (Edge<V, E> edg : g.outgoingEdges(vOrig)) {
                V vAdj = g.opposite(vOrig, edg);
                int kAdj = g.getKey(vAdj);
                if (!visited[kAdj] && dist[kAdj] > (dist[kOrg] + edg.getWeight())) {
                    dist[kAdj] = dist[kOrg] + edg.getWeight();
                    pathKeys[kAdj] = kOrg;
                }
            }
            i = getVertMinDist(dist, visited);
        }
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf The path
     * is constructed from the end to the beginning
     *
     * @param g Graph instance
     * @param voInf information of the Vertex origin
     * @param vdInf information of the Vertex destination
     * @param pathkeys minimum path vertices keys
     * @param path stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, LinkedList<V> path) {

        if (!vOrig.equals(vDest)) {
            path.push(vDest);

            int vKey = g.getKey(vDest);
            int prevVKey = pathKeys[vKey];
            vDest = verts[prevVKey];

            getPath(g, vOrig, vDest, verts, pathKeys, path);
        } else {
            path.push(vOrig);
        }
    }

    //shortest-path between voInf and vdInf
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return -1;
        }
        boolean[] visited = new boolean[g.numVertices()];
        int[] pathKeys = new int[g.numVertices()];
        double[] dist = new double[g.numVertices()];
        V vertices[] = g.allkeyVerts();
        shortPath.clear();

        for (V v : g.vertices()) {
            dist[g.getKey(v)] = Double.MAX_VALUE;
            pathKeys[g.getKey(v)] = -1;
            visited[g.getKey(v)] = false;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);

        double lengthPath = dist[g.getKey(vDest)];

        if (lengthPath != Double.MAX_VALUE) {
            getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);
            return lengthPath;
        }
        return -1;
    }

    /**
     * Reverses the path
     *
     * @param path stack with path
     */
    private static <V, E> LinkedList<V> revPath(LinkedList<V> path) {

        LinkedList<V> pathcopy = new LinkedList<>(path);
        LinkedList<V> pathrev = new LinkedList<>();

        while (!pathcopy.isEmpty()) {
            pathrev.push(pathcopy.pop());
        }

        return pathrev;
    }

    private static int getVertMinDist(double[] dist, boolean[] visited) {
        double min = Double.MAX_VALUE;
        int indice = -1;

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && (dist[i] < min)) {
                min = dist[i];
                indice = i;
            }
        }

        return indice;
    }
    //shortest-path in edges between voInf and vdInf

    public static <V, E> double shortestPathEdges(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return -1;
        }
        int[] pathKeys = new int[g.numVertices()];
        double[] dist = new double[g.numVertices()];
        V vertices[] = g.allkeyVerts();
        shortPath.clear();

        for (V v : g.vertices()) {
            dist[g.getKey(v)] = Double.MAX_VALUE;
            pathKeys[g.getKey(v)] = -1;
        }

        shortestPathEdges(g, vOrig, pathKeys, dist);

        double lengthPath = dist[g.getKey(vDest)];

        if (lengthPath != Double.MAX_VALUE) {
            getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);
            return lengthPath;
        }
        return -1;
    }

    private static <V, E> void shortestPathEdges(Graph<V, E> g, V vOrig, int[] pathKeys, double[] dist) {

        int vOrigkey = g.getKey(vOrig);
        dist[vOrigkey] = 0;
        LinkedList<V> queue = new LinkedList<>();
        queue.add(vOrig);
        while (!queue.isEmpty()) {
            vOrig = queue.pop();
            for (V vAdj : g.adjVertices(vOrig)) {
                int vAdjKey = g.getKey(vAdj);
                if (dist[vAdjKey] == Double.MAX_VALUE) {
                    dist[vAdjKey] = dist[vOrigkey] + 1;
                    pathKeys[vAdjKey] = g.getKey(vOrig);
                    queue.push(vAdj);
                }

            }
        }
    }
}
