/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphexamples;

import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit tests class for LabyrinthCheater class
 *
 * @author DEI-ESINF
 *
 */
public class LabyrinthCheaterTest {

    LabyrinthCheater map = new LabyrinthCheater();

    @Before
    public void setUp() throws Exception {

        // See Map.png in the source folder of the test
        for (char c = 'A'; c < 'E'; c++) {
            map.insertRoom(String.valueOf(c), false);
        }
        map.insertRoom("E", true);
        for (char c = 'F'; c < 'J'; c++) {
            map.insertRoom(String.valueOf(c), false);
        }
        map.insertRoom("J", true);
        map.insertRoom("K", true);
        for (char c = 'L'; c < 'S'; c++) {
            map.insertRoom(String.valueOf(c), false);
        }
        map.insertRoom("S", true);
        map.insertRoom("T", false);

        map.insertDoor("A", "B");
        map.insertDoor("B", "G");
        map.insertDoor("G", "H");
        map.insertDoor("H", "C");
        map.insertDoor("C", "D");
        map.insertDoor("D", "E");
        map.insertDoor("D", "I");
        map.insertDoor("A", "F");
        map.insertDoor("K", "L");
        map.insertDoor("I", "J");
        map.insertDoor("F", "K");
        map.insertDoor("L", "Q");
        map.insertDoor("L", "M");
        map.insertDoor("M", "N");
        map.insertDoor("N", "S");

    }

    @Test
    public void testInsertRoom() {
        System.out.println("Test of insert room");

        LabyrinthCheater tempMap = new LabyrinthCheater();

        assertTrue("result should be true", tempMap.insertRoom("A", false));
        assertTrue("result should be true", tempMap.insertRoom("B", false));

        assertFalse("duplicate room should fail", tempMap.insertRoom("A", true));
    }

    @Test
    public void testInsertDoor() {
        System.out.println("Test of insert door");

        LabyrinthCheater tempMap = new LabyrinthCheater();

        tempMap.insertRoom("A", false);
        tempMap.insertRoom("B", false);

        tempMap.insertRoom("C", true);
        tempMap.insertRoom("D", false);

        assertTrue("result should be true", tempMap.insertDoor("A", "B"));
        assertTrue("result should be true", tempMap.insertDoor("C", "B"));
        assertTrue("result should be true", tempMap.insertDoor("D", "A"));

        assertFalse("should fail on inexistent room", tempMap.insertDoor("A", "E"));

        assertFalse("should fail on already existent door", tempMap.insertDoor("A", "D"));

    }

    @Test
    public void testRoomsInReach() {
        System.out.println("Test Rooms in Reach");
        LabyrinthCheater tempMap = new LabyrinthCheater();

        tempMap.insertRoom("A", false);
        tempMap.insertRoom("B", false);
        tempMap.insertRoom("C", true);
        tempMap.insertRoom("D", false);
        tempMap.insertRoom("E", false);
        tempMap.insertRoom("F", false);
        tempMap.insertRoom("G", true);

        tempMap.insertDoor("A", "B");
        tempMap.insertDoor("B", "C");

        tempMap.insertDoor("D", "E");
        tempMap.insertDoor("E", "F");
        tempMap.insertDoor("F", "G");

        Iterable<String> iterable = tempMap.roomsInReach("A");
        boolean containsA = false;
        boolean containsB = false;
        boolean containsC = false;
        for (String s : iterable) {
            if (s.equals("A")) {
                containsA = true;
            }
            if (s.equals("B")) {
                containsB = true;
            }
            if (s.equals("C")) {
                containsC = true;
            }
        }
        assertTrue("Contains A", containsA);
        assertTrue("Contains B", containsB);
        assertTrue("Contains C", containsC);
    }

    @Test
    public void testNearestExit() {
        System.out.println("Teste nearest Exist");

        LabyrinthCheater tempMap = new LabyrinthCheater();

        tempMap.insertRoom("A", false);
        tempMap.insertRoom("B", false);
        tempMap.insertRoom("C", true);
        tempMap.insertRoom("D", false);
        tempMap.insertRoom("E", false);
        tempMap.insertRoom("F", false);
        tempMap.insertRoom("G", true);

        tempMap.insertDoor("A", "B");
        tempMap.insertDoor("B", "C");

        tempMap.insertDoor("A", "D");
        tempMap.insertDoor("D", "E");
        tempMap.insertDoor("E", "F");
        tempMap.insertDoor("F", "G");

        String nearesExit = tempMap.nearestExit("A");
        assertTrue("A saida mais proxima é C", nearesExit.equals("C"));
    }

    @Test
    public void testPathToExit() {
        System.out.println("Teste Path to Exit");
        LabyrinthCheater tempMap = new LabyrinthCheater();

        tempMap.insertRoom("A", false);
        tempMap.insertRoom("B", false);
        tempMap.insertRoom("C", false);
        tempMap.insertRoom("D", false);
        tempMap.insertRoom("E", false);
        tempMap.insertRoom("F", false);
        tempMap.insertRoom("G", true);

        tempMap.insertDoor("A", "B");
        tempMap.insertDoor("B", "C");

        tempMap.insertDoor("A", "D");
        tempMap.insertDoor("D", "E");
        tempMap.insertDoor("E", "F");
        tempMap.insertDoor("F", "G");

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("A");
        expectedResult.add("D");
        expectedResult.add("E");
        expectedResult.add("F");
        expectedResult.add("G");

        LinkedList<String> result = tempMap.pathToExit("A");

        assertEquals(expectedResult, result);

        tempMap = new LabyrinthCheater();

        tempMap.insertRoom("A", false);
        tempMap.insertRoom("B", false);
        tempMap.insertRoom("C", false);
        tempMap.insertRoom("D", false);
        tempMap.insertRoom("E", false);
        tempMap.insertRoom("F", false);
        tempMap.insertRoom("G", false);

        tempMap.insertDoor("A", "B");
        tempMap.insertDoor("B", "C");

        tempMap.insertDoor("A", "D");
        tempMap.insertDoor("D", "E");
        tempMap.insertDoor("E", "F");
        tempMap.insertDoor("F", "G");

        result = tempMap.pathToExit("A");
        assertNull("Não existe caminho", result);

    }

}
