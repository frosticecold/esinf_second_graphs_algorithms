
package graph;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * Implementation of graph algorithms for a (undirected) graph structure 
 * Considering generic vertex V and edge E types
 * 
 * Works on AdjancyMatrixGraph objects
 * 
 * @author DEI-ESINF
 * 
 */
public class GraphAlgorithms {

    private static <T> LinkedList<T> reverse(LinkedList <T> list){
        LinkedList<T> reversed = new LinkedList<T>();
        Iterator<T> it = list.iterator();
        while(it.hasNext()) reversed.push(it.next());
        return reversed;
    }

    /**
     * Performs depth-first search of the graph starting at vertex.
     * Calls package recursive version of the method.
     *
     * @param graph Graph object
     * @param vertex Vertex of graph that will be the source of the search
     * @return queue of vertices found by search (including vertex), null if vertex does not exist
     *
     */
    public static <V,E> LinkedList<V> DFS(AdjacencyMatrixGraph<V,E> graph, V vertex) {

        int index = graph.toIndex(vertex);
        if (index == -1)
            return null;

        LinkedList<V> resultQueue = new LinkedList<V>();
        resultQueue.add(vertex);
        boolean [] knownVertices = new boolean[graph.numVertices];
        DFS(graph, index, knownVertices, resultQueue);
        return resultQueue;
    }

    /**
     * Actual depth-first search of the graph starting at vertex.
     * The method adds discovered vertices (including vertex) to the queue of vertices
     *
     * @param graph Graph object
     * @param index Index of vertex of graph that will be the source of the search
     * @param known previously discovered vertices
     * @param verticesQueue queue of vertices found by search
     *
     */
    static <V,E> void DFS(AdjacencyMatrixGraph<V,E> graph, int index, boolean [] knownVertices, LinkedList<V> verticesQueue) {
        knownVertices[index] = true;                              
        for (int i = 0 ; i < graph.numVertices ; i++) {     
            if(graph.edgeMatrix[index][i] != null && knownVertices[i] == false){
                verticesQueue.add(graph.vertices.get(i));
                DFS(graph, i, knownVertices, verticesQueue);
            }
        }
    }

    /**
     * Performs breath-first search of the graph starting at vertex.
     * The method adds discovered vertices (including vertex) to the queue of vertices
     *	
     * @param graph Graph object
     * @param vertex Vertex of graph that will be the source of the search
     * @return queue of vertices found by search (including vertex), null if vertex does not exist
     *
     */
    public static <V,E> LinkedList<V> BFS(AdjacencyMatrixGraph<V,E> graph, V vertex) {
		throw new UnsupportedOperationException("Not supported yet.");	
    }

    /**
     * All paths between two vertices 
     * Calls recursive version of the method.
     *
     * @param graph Graph object
     * @param source Source vertex of path 
     * @param dest Destination vertex of path
     * @param path LinkedList with paths (queues)
     * @return false if vertices not in the graph
     *
     */
    public static <V,E> boolean allPaths(AdjacencyMatrixGraph<V,E> graph, V source, V dest, LinkedList<LinkedList<V>> paths) {
		throw new UnsupportedOperationException("Not supported yet.");	
    }
    
    
    
    /**
     * Actual paths search 
     * The method adds vertices to the current path (stack of vertices)
     * when destination is found, the current path is saved to the list of paths 
     *
     * @param graph Graph object
     * @param sourceIdx Index of source vertex 
     * @param destIdx Index of destination vertex
     * @param knownVertices previously discovered vertices
     * @param auxStack stack of vertices in the path
     * @param path LinkedList with paths (queues)
     * 
     */
    static <V,E> void allPaths(AdjacencyMatrixGraph<V,E> graph, int sourceIdx, int destIdx, boolean [] knownVertices, LinkedList<V> auxStack, LinkedList<LinkedList<V>> paths) {
		throw new UnsupportedOperationException("Not supported yet.");	
    }

    /**
     * Transforms a graph into its transitive closure 
     * uses the Floyd-Warshall algorithm
     * 
     * @param graph Graph object
     * @param dummyEdge object to insert in the newly created edges
     * @return the new graph 
     */
    public static <V, E> AdjacencyMatrixGraph<V, E> transitiveClosure(AdjacencyMatrixGraph<V, E> graph, E dummyEdge){
            throw new UnsupportedOperationException("Not supported yet.");	
    }

}
