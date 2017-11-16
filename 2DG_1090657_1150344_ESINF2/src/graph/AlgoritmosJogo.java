/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.Collections;
import java.util.LinkedList;
import model.Local;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class AlgoritmosJogo {

    /**
     * Determine the shortest path to all vertices from a vertex using
     * Dijkstra's algorithm To be called by public short method
     *
     * @param graph Graph object
     * @param sourceIdx Source vertex
     * @param knownVertices previously discovered vertices
     * @param verticesIndex index of vertices in the minimum path
     * @param minDist minimum distances in the path
     *
     */
    private static void shortestPathConquista(AdjacencyMatrixGraph<Local, Double> graph, int sourceIdx, boolean[] knownVertices, int[] verticesIndex, double[] minDist) {
        minDist[sourceIdx] = 0;
        while (sourceIdx != -1) {
            knownVertices[sourceIdx] = true;
            for (Local vx : graph.vertices()) {
                int vAdj = graph.toIndex(vx);
                Double edge = graph.privateGet(sourceIdx, vAdj);
                if (edge != null) {
                    if (vx.getDono() == null) {
                        if (!knownVertices[vAdj] && minDist[vAdj] > minDist[sourceIdx] + edge + vx.getDificuldade()) {
                            minDist[vAdj] = minDist[sourceIdx] + edge + vx.getDificuldade();
                            verticesIndex[vAdj] = sourceIdx;
                        }
                    }
                    if (!knownVertices[vAdj] && minDist[vAdj] > minDist[sourceIdx] + edge + vx.getDificuldade() + vx.getDono().getForca()) {
                        minDist[vAdj] = minDist[sourceIdx] + edge + vx.getDificuldade() + vx.getDono().getForca();
                        verticesIndex[vAdj] = sourceIdx;
                    }
                }
            }

            Double min = Double.MAX_VALUE;
            sourceIdx = -1;
            for (int i = 0; i < graph.numVertices; i++) {
                if (!knownVertices[i] && minDist[i] < min) {
                    min = minDist[i];
                    sourceIdx = i;
                }
            }
        }
    }

    /**
     * Determine the shortest path between two vertices using Dijkstra's
     * algorithm
     *
     * @param graph Graph object
     * @param source Source vertex
     * @param dest Destination vertices
     * @param path Returns the vertices in the path (empty if no path)
     * @return minimum distance, -1 if vertices not in graph or no path
     *
     */
    public static double shortestPathConquista(AdjacencyMatrixGraph<Local, Double> graph, Local source, Local dest, LinkedList<Local> path) {
        int sourceIdx = graph.toIndex(source);
        if (sourceIdx == -1) {
            return -1;
        }

        int destIdx = graph.toIndex(dest);
        if (destIdx == -1) {
            return -1;
        }

        boolean[] knownVertices = new boolean[graph.numVertices];
        int[] verticesIndex = new int[graph.numVertices];
        double[] minDist = new double[graph.numVertices];

        path.clear();

        for (Local v : graph.vertices()) {
            minDist[graph.toIndex(v)] = Double.MAX_VALUE;
            verticesIndex[graph.toIndex(v)] = -1;
            knownVertices[graph.toIndex(v)] = false;
        }

        shortestPathConquista(graph, sourceIdx, knownVertices, verticesIndex, minDist);

        if (knownVertices[destIdx] == false) {
            return -1;
        }

        recreatePath(graph, sourceIdx, destIdx, verticesIndex, path);
        Collections.reverse(path);
        return minDist[destIdx];
    }

    /**
     * Recreates the minimum path between two vertex, from the result of
     * Dikstra's algorithm
     *
     * @param graph Graph object
     * @param sourceIdx Source vertex
     * @param destIdx Destination vertices
     * @param verticesIndex index of vertices in the minimum path
     * @param Queue Vertices in the path (empty if no path)
     */
    private static void recreatePath(AdjacencyMatrixGraph<Local, Double> graph, int sourceIdx, int destIdx, int[] verticesIndex, LinkedList<Local> path) {

        path.add(graph.vertices.get(destIdx));
        if (sourceIdx != destIdx) {
            destIdx = verticesIndex[destIdx];
            recreatePath(graph, sourceIdx, destIdx, verticesIndex, path);
        }
    }
}
