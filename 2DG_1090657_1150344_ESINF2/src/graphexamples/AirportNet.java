package graphexamples;

import graphbase.Edge;
import graphbase.Graph;
import graphbase.GraphAlgorithms;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author DEI-ESINF
 */
public class AirportNet {

    private Graph<String, Integer> airport;

    public AirportNet() {
        airport = new Graph<>(false);
    }

    public void addAirport(String a) {
        airport.insertVertex(a);
    }

    public void addRoute(String a1, String a2, double miles, Integer npasseng) {

        airport.insertEdge(a1, a2, npasseng, miles);
    }

    public int keyAirport(String airp) {

        return airport.getKey(airp);
    }

    public String airportbyKey(int key) {

        String[] allkeyVerts = airport.allkeyVerts();
        if (key < 0 || key >= allkeyVerts.length) {
            return null;
        }
        return allkeyVerts[key];
    }

    public Integer trafficAirports(String airp1, String airp2) {

        Edge<String, Integer> edge = airport.getEdge(airp1, airp2);
        if (edge == null) {
            return null;
        }
        return edge.getElement();
    }

    public Double milesAirports(String airp1, String airp2) {

        Edge<String, Integer> edge = airport.getEdge(airp1, airp2);
        if (edge == null) {
            return null;
        }
        return edge.getWeight();
    }

    public String nroutesAirport() {

        Map<String, Integer> m = new HashMap<>();
        for (String airp : airport.vertices()) {
            int grau = airport.outDegree(airp) + airport.inDegree(airp);
            m.put(airp, grau);
        }
        return m.toString();
    }

    public Map<String, String> AirpMaxMiles() {

        Map<String, String> airpMaxMiles = new HashMap<>();
        double maxMiles = Double.MIN_VALUE;
        for (Edge<String, Integer> edge : airport.edges()) {
            if (maxMiles <= edge.getWeight()) {
                if (maxMiles < edge.getWeight()) {
                    maxMiles = edge.getWeight();
                    airpMaxMiles.clear();
                }
                airpMaxMiles.put(edge.getVOrig(), edge.getVDest());
            }
        }
        return airpMaxMiles;
    }

    public Boolean ConnectAirports(String airp1, String airp2) {

        LinkedList<String> qairps = GraphAlgorithms.DepthFirstSearch(airport, airp1);

        return qairps.contains(airp2);
    }

    @Override
    public String toString() {
        return airport.toString();
    }
}
