package winter.advent.advent2022;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;

@Getter
public class RopeBridge {
    private final List<Knot> knots;
    private final List<Move> movesToMake = new LinkedList<>();

    public static RopeBridge fromLines(int knotCount, List<String> lines) {
        List<Move> moves = lines.stream()
                .map(l -> l.split(" "))
                .map(s -> new Move(Direction.valueOf(s[0]), Integer.parseInt(s[1])))
                .toList();
        return new RopeBridge(knotCount, moves);
    }

    public RopeBridge(int knotCount, List<Move> moves) {
        this.movesToMake.addAll(moves);
        this.knots = IntStream.range(0, knotCount)
                .mapToObj(i -> new Knot(new Location(0,0)))
                .toList();
    }

    public RopeBridge run(){
        movesToMake.stream()
                .flatMap(Move::decompose)
                .forEach(move -> {
                    Knot headKnot = knots.get(0).move(move);
                    knots.stream().skip(1)
                            .reduce(headKnot, (k1, k2) -> k2.moveTowards(k1));
                });
        return this;
    }

    Set<Location> getDistinctTailLocations(){
        return Set.copyOf(getTailLocations());
    }

    List<Location> getTailLocations(){
        return knots.get(knots.size()-1).locationHistory;
    }

    @Getter
    public static class Knot {
        private Location location;

        private List<Location> locationHistory = new LinkedList<>();

        public Knot(Location location) {
            moveTo(location);
        }

        public Knot moveTo(Location location){
            this.location = location;
            locationHistory.add(location);
            return this;
        }

        public Knot moveTowards(Knot k1) {
            moveTo(location.ifMovedTowards(k1.location));
            return this;
        }

        public Knot move(Move move) {
            return moveTo(location.ifMoved(move));
        }
    }

    public record Move(Direction direction, int distance) {

        public Stream<Move> decompose(){
            return IntStream.range(0, distance)
                    .mapToObj(i -> new Move(direction, 1));
        }
    }

    public record Location(int x, int y) {

        public Location ifMoved(Move move){
            return ifMoved(move.direction.delta[0] * move.distance, move.direction.delta[1] * move.distance);
        }

        public Location ifMoved(int xDelta, int yDelta){
            return new Location(x + xDelta, y + yDelta);
        }

        public Location ifMovedTowards(Location other){

            int distanceX = other.x - x;
            int distanceY = other.y - y;

            int newX = x;
            int newY = y;
            if(abs(distanceX) > 1) {
                if(other.y != y) {
                    newY = other.y;
                }
                newX = x + Integer.compare(other.x, x);
            }
            if(abs(distanceY) > 1) {
                if(other.x != x) {
                    newX = other.x;
                }
                newY = y + Integer.compare(other.y, y);
            }
            return new Location(newX, newY);
        }
    }

    public enum Direction {
        U(0, 1), D(0, -1), L(-1, 0), R(1, 0);

        private final int[] delta;

        Direction(int... delta) {
            this.delta = delta;
        }
    }

}
