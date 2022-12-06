package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TuningTroubleTest {

    @Test
    public void testFindStartOfPacketIndices_short() {
        assertEquals(5, new TuningTrouble.Datastream("bvwbjplbgvbhsrlpgdmjqwftvncz").findStartOfPacketIndices().findFirst().orElseThrow());
        assertEquals(6, new TuningTrouble.Datastream("nppdvjthqldpwncqszvftbrmjlhg").findStartOfPacketIndices().findFirst().orElseThrow());
        assertEquals(10, new TuningTrouble.Datastream("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").findStartOfPacketIndices().findFirst().orElseThrow());
        assertEquals(11, new TuningTrouble.Datastream("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").findStartOfPacketIndices().findFirst().orElseThrow());
    }

    @Test
    public void testFindStartOfPacketIndices_long() {
        assertEquals(1655, TuningTrouble.Datastream.fromStream(getClass().getResourceAsStream("/d6/tuning-trouble.txt"))
                .findStartOfPacketIndices().findFirst().orElseThrow());
    }

    @Test
    public void testFindStartOfPacketIndices_long_messages() {
        assertEquals(2665, TuningTrouble.Datastream.fromStream(getClass().getResourceAsStream("/d6/tuning-trouble.txt"))
                .setMinStartSize(14)
                .findStartOfPacketIndices()
                .findFirst().orElseThrow());
    }

}