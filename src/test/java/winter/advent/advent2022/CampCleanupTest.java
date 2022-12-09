package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampCleanupTest {

    @Test
    void fullyRedudentCount_simple(){
        CampCleanup campCleanup = new CampCleanup(
                CampCleanup.Pairing.fromString("2-4,6-8"),
                CampCleanup.Pairing.fromString("2-3,4-5"),
                CampCleanup.Pairing.fromString("5-7,7-9"),
                CampCleanup.Pairing.fromString("2-8,3-7"),
                CampCleanup.Pairing.fromString("6-6,4-6"),
                CampCleanup.Pairing.fromString("2-6,4-8")
        );
        assertEquals(2, campCleanup.getRedundentElfAssignments().size());
    }

    @Test
    void fullyRedudentCount_full(){
        CampCleanup campCleanup = CampCleanup.loadFromInputStream(getClass().getResourceAsStream("/inputfiles/camp-cleanup.txt"));
        assertEquals(569, campCleanup.getRedundentElfAssignments().size());
    }

    @Test
    void getOverlappingPairints_simple(){
        CampCleanup campCleanup = new CampCleanup(
                CampCleanup.Pairing.fromString("2-4,6-8"),
                CampCleanup.Pairing.fromString("2-3,4-5"),
                CampCleanup.Pairing.fromString("5-7,7-9"),
                CampCleanup.Pairing.fromString("2-8,3-7"),
                CampCleanup.Pairing.fromString("6-6,4-6"),
                CampCleanup.Pairing.fromString("2-6,4-8")
        );
        assertEquals(4, campCleanup.getOverlappingPairings().size());
    }

    @Test
    void overlappingPairings(){
        CampCleanup campCleanup = CampCleanup.loadFromInputStream(getClass().getResourceAsStream("/inputfiles/camp-cleanup.txt"));
        assertEquals(936, campCleanup.getOverlappingPairings().size());
    }

}