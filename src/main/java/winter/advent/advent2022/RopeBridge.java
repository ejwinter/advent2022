package winter.advent.advent2022;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;

public class RopeBridge {
    private final int[] start = new int[]{0,0};
    private final int[] headLocation = new int[]{0, 0};
    private final int[] tailLocation = new int[]{0, 0};

    private final List<Move> headMoveLog;

    private final List<Location> tailPositionLog = new LinkedList<>();

    public static RopeBridge fromLines(List<String> lines) {
        List<Move> moves = lines.stream()
                .map(l -> l.split(" "))
                .map(s -> new Move(Direction.valueOf(s[0]), Integer.parseInt(s[1])))
                .toList();
        return new RopeBridge(moves);
    }

    public RopeBridge(List<Move> moves) {
        this.headMoveLog = moves;
    }

    public void run(){
        headMoveLog.forEach(this::makeMove);
    }

    public Set<Location> getDistinctTailPositions(){
        System.out.println(tailPositionLog.size());
        return Set.copyOf(tailPositionLog);
    }

    public void makeMove(Move move){
        for(int step = move.distance; step > 0; step--) {

            headLocation[0] = headLocation[0] + move.direction.delta[0];
            headLocation[1] = headLocation[1] + move.direction.delta[1];

            int[] distance = getDistance();
            //System.out.println("distance: " + distance[0] + " " + distance[1]);

            final int[] shift = new int[]{0, 0};
            if(abs(distance[0]) > 1) {
                if(tailLocation[1] != headLocation[1]) {
                    tailLocation[1] = headLocation[1];
                }
                shift[0] = towards(distance[0]);
            }
            if(abs(distance[1]) > 1) {
                if(tailLocation[0] != headLocation[0]) {
                    tailLocation[0] = headLocation[0];
                }
                shift[1] = towards(distance[1]);
            }
            //System.out.println("shift: " + shift[0] + " " + shift[1]);
            tailLocation[0] = tailLocation[0] + shift[0];
            tailLocation[1] = tailLocation[1] + shift[1];
            tailPositionLog.add(new Location(tailLocation[0], tailLocation[1]));
            System.out.println("Head: " + headLocation[0] + ", " + headLocation[1] + " Tail: " + tailLocation[0] + ", " + tailLocation[1]);
        }
    }

    private int towards(int i) {
        if(i == 0){
            return 0;
        }
        return i / abs(i);
    }


    public int[] getDistance(){
        return new int[]{headLocation[0] - tailLocation[0], headLocation[1] - tailLocation[1]};
    }

    public record Move(Direction direction, int distance) {

    }

    public record Location(int x, int y) {

    }

    public enum Direction {
        U(0, 1), D(0, -1), L(-1, 0), R(1, 0);

        private final int[] delta;

        Direction(int... delta) {
            this.delta = delta;
        }
    }

}
