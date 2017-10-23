package graphexamples;

import graph.AdjacencyMatrixGraph;
import graph.GraphAlgorithms;
import java.util.LinkedList;

/**
 * A class to represent a labyrinth map with rooms, doors and exits Methods
 * discover the nearest exit and the path to it Stores a graph of private Room
 * vertex and Door edges
 *
 * @author DEI-ESINF
 *
 */
public class LabyrinthCheater {

    private class Room {

        public String name;
        public boolean hasExits;

        public Room(String n, boolean exit) {
            name = n;
            hasExits = exit;
        }

        public boolean equals(Object other) {
            if (!(other instanceof Room)) {
                return false;
            }
            return name.equals(((Room) other).name);
        }

    }

    private class Door {
    }

    AdjacencyMatrixGraph<Room, Door> map;

    public LabyrinthCheater() {
        map = new AdjacencyMatrixGraph<>();
    }

    /**
     * Inserts a new room in the map
     *
     * @param name
     * @param hasExit
     * @return false if city exists in the map
     */
    public boolean insertRoom(String name, boolean hasExit) {
        Room r = new Room(name, hasExit);
        if (!map.checkVertex(r)) {
            return map.insertVertex(r);
        } else {
            return false;
        }
    }

    /**
     * Inserts a new door in the map fails if room does not exist or door
     * already exists
     *
     * @param from room
     * @param to room
     * @return false if a room does not exist or door already exists between
     * rooms
     */
    public boolean insertDoor(String from, String to) {
        Room rfrom = new Room(from, false);
        Room rto = new Room(to, false);
        if (!map.checkVertex(rfrom)) {
            return false;
        }
        if (!map.checkVertex(rto)) {
            return false;
        }

        if (map.getEdge(rfrom, rto) == null) {
            return map.insertEdge(rfrom, rto, new Door());
        }
        return false;
    }

    /**
     * Returns a list of rooms which are reachable from one room
     *
     * @param room
     * @return list of room names or null if room does not exist
     */
    public Iterable<String> roomsInReach(String room) {
        if (!map.checkVertex(new Room(room, false))) {
            return null;
        }

        LinkedList<String> names = new LinkedList<>();

        LinkedList<Room> rooms = GraphAlgorithms.DFS(map, new Room(room, false));

        for (Room r : rooms) {
            names.add(r.name);
        }

        return names;
    }

    /**
     * Returns the nearest room with an exit
     *
     * @param room from room
     * @return room name or null if from room does not exist or there is no
     * reachable exit
     */
    public String nearestExit(String room) {
        if (!map.checkVertex(new Room(room, false))) {
            return null;
        }

        LinkedList<Room> rooms = GraphAlgorithms.BFS(map, new Room(room, false));
        for (Room r : rooms) {
            if (r.hasExits) {
                return r.name;
            }
        }
        return null;
    }

    /**
     * Returns the shortest path to the nearest room with an exit
     *
     * @param from room
     * @return list of room names or null if from room does not exist or there
     * is no reachable exit
     */
    public LinkedList<String> pathToExit(String from) {
        LinkedList<Room> listaRooms = new LinkedList<>();
        if (!map.checkVertex(new Room(from, false))) {
            return null;
        }
        Room r = new Room(from, false);

        boolean foundExit = false;
        LinkedList<String> listaNomes = new LinkedList<>();
        listaRooms = GraphAlgorithms.DFS(map, r);
        for (Room room : listaRooms) {
            if (!room.hasExits) {
                listaNomes.add(room.name);
            } else {
                foundExit = true;
                listaNomes.add(room.name);
                break;
            }
        }
        if (!foundExit) {
            return null;
        }
        return listaNomes;
    }

}
