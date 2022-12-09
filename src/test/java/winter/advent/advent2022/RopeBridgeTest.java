package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RopeBridgeTest {

    @Test
    void testGetTailPositions(){
        var bridge = RopeBridge.fromLines(IOUtils.listAllLines(getClass().getResourceAsStream("/d9/short-moves.txt")));
        bridge.run();
        var tailPositions = bridge.getDistinctTailPositions();
        assertEquals(13, tailPositions.size());

    }

    @Test
    void testGetTailPositionsLong(){
        var bridge = RopeBridge.fromLines(IOUtils.listAllLines(getClass().getResourceAsStream("/d9/long-moves.txt")));
        bridge.run();
        var tailPositions = bridge.getDistinctTailPositions();
        assertEquals(6384, tailPositions.size());

    }

}