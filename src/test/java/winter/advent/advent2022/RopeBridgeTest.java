package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RopeBridgeTest {

    @Test
    void testGetTailPositions(){
        var bridge = RopeBridge.fromLines(2, IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/short-moves.txt"))).run();

        bridge.getKnots().get(0).getLocationHistory().forEach(System.out::println);
        bridge.getKnots().get(1).getLocationHistory().forEach(System.out::println);

        var tailPositions = bridge.getDistinctTailLocations();
        assertEquals(13, tailPositions.size());

    }

    @Test
    void testGetTailPositionsLong(){
        var bridge = RopeBridge.fromLines(2, IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/long-moves.txt"))).run();
        var tailPositions = bridge.getDistinctTailLocations();
        assertEquals(6384, tailPositions.size());
    }



    @Test
    void testGetTailPositions_Part2(){
        var bridge = RopeBridge.fromLines(10, IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/short-moves.txt"))).run();
        var tailPositions = bridge.getDistinctTailLocations();
        assertEquals(1, tailPositions.size());

    }

    @Test
    void testGetTailPositions_Part2_mid(){
        var bridge = RopeBridge.fromLines(10, IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/mid-moves.txt"))).run();
        var tailPositions = bridge.getDistinctTailLocations();
        assertEquals(36, tailPositions.size());

    }

    @Test
    void testGetTailPositionsLong_Part2(){
        var bridge = RopeBridge.fromLines(10, IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/long-moves.txt"))).run();
        var tailPositions = bridge.getDistinctTailLocations();
        // too much time, smaller examples work this one is too big to debug
        assertEquals(-1, tailPositions.size());
    }

}