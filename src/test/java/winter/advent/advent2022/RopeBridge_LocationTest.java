package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RopeBridge_LocationTest {

    @Test
    void moveTowardsDiagonallySame() {
        var leader = new RopeBridge.Location(0, 0);
        var follower = new RopeBridge.Location(0, 0);

        RopeBridge.Location newFollower = follower.ifMovedTowards(leader);
        assertEquals(0, newFollower.x());
        assertEquals(0, newFollower.y());
    }

    @Test
    void moveTowardsDiagonallyAdjacent() {
        var leader = new RopeBridge.Location(1, 0);
        var follower = new RopeBridge.Location(0, 0);

        RopeBridge.Location newFollower = follower.ifMovedTowards(leader);
        assertEquals(0, newFollower.x());
        assertEquals(0, newFollower.y());
    }

    @Test
    void moveTowardsDiagonallyOneX() {
        var leader = new RopeBridge.Location(2, 0);
        var follower = new RopeBridge.Location(0, 0);

        RopeBridge.Location newFollower = follower.ifMovedTowards(leader);
        assertEquals(1, newFollower.x());
        assertEquals(0, newFollower.y());
    }

    @Test
    void moveTowardsDiagonallyOneY() {
        var leader = new RopeBridge.Location(0, 2);
        var follower = new RopeBridge.Location(0, 0);

        RopeBridge.Location newFollower = follower.ifMovedTowards(leader);
        assertEquals(0, newFollower.x());
        assertEquals(1, newFollower.y());
    }

    @Test
    void moveTowardsDiagonallyDiagY() {
        var leader = new RopeBridge.Location(1, 2);
        var follower = new RopeBridge.Location(0, 0);

        RopeBridge.Location newFollower = follower.ifMovedTowards(leader);
        assertEquals(1, newFollower.x());
        assertEquals(1, newFollower.y());
    }


    @Test
    void moveTowardsDiagonallyDiagX() {
        var leader = new RopeBridge.Location(2, 1);
        var follower = new RopeBridge.Location(0, 0);

        var newFollower = follower.ifMovedTowards(leader);
        assertEquals(1, newFollower.x());
        assertEquals(1, newFollower.y());
    }

    @Test
    void moveTowardsDiagonallyDiagNegX() {
        var leader = new RopeBridge.Location(-2, -1);
        var follower = new RopeBridge.Location(0, 0);

        var newFollower = follower.ifMovedTowards(leader);
        assertEquals(-1, newFollower.x());
        assertEquals(-1, newFollower.y());
    }

}