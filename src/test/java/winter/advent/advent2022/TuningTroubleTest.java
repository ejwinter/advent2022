package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TuningTroubleTest {

    @Test
    public void testFindStartOfPacketIndices_short() {
        assertEquals(5, new TuningTrouble.Datastream("bvwbjplbgvbhsrlpgdmjqwftvncz").findPacketMarkers().findFirst().get());
        assertEquals(6, new TuningTrouble.Datastream("nppdvjthqldpwncqszvftbrmjlhg").findPacketMarkers().findFirst().get());
        assertEquals(10, new TuningTrouble.Datastream("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").findPacketMarkers().findFirst().get());
        assertEquals(11, new TuningTrouble.Datastream("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").findPacketMarkers().findFirst().get());
    }

    @Test
    public void testFindStartOfPacketIndices_long() {
        assertEquals(1655, TuningTrouble.Datastream.fromStream(getClass().getResourceAsStream("/d6/tuning-trouble.txt"))
                .findStreamMarker(4).findFirst().get());
    }

    @Test
    public void testFindStartOfPacketIndices_long_messages() {
        assertEquals(2665, TuningTrouble.Datastream.fromStream(getClass().getResourceAsStream("/d6/tuning-trouble.txt"))
                .findMessageMarkers()
                .findFirst().get());
    }

}