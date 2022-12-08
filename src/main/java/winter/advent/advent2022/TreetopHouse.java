package winter.advent.advent2022;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public class TreetopHouse {
    private final Grove grove;

    public TreetopHouse(Grove grove) {
        this.grove = grove;
    }

    public static class Grove {
        public Grove(int[][] trees) {
            this.trees = trees;
        }

        private final int[][] trees;

        public static Grove fromStream(InputStream inputStream) {
            List<String> lines = IOUtils.listAllLines(inputStream);
            return Grove.fromLines(lines);
        }

        public static Grove fromLines(List<String> lines) {
            final int size = lines.size();
            int[][] trees = new int[size][size];
            for (int row = 0; row < size; row++) {
                String line = lines.get(row);
                for (int col = 0; col < size; col++) {
                    trees[row][col] = Character.getNumericValue(line.charAt(col));
                }
            }
            return new Grove(trees);
        }

        public Tree getTree(int row, int col) {
            return new Tree(row, col);
        }

        public Stream<Tree> streamTrees() {
            return IntStream.range(0, trees.length)
                    .mapToObj(row -> {
                        return IntStream.range(0, trees.length).mapToObj(col -> new Tree(row, col));
                    })
                    .flatMap(treeStream -> treeStream);
        }

        public Tree mostScenicTree(){
            return streamTrees()
                    .reduce((tree1, tree2) -> tree1.calcScenicScore() > tree2.calcScenicScore() ? tree1 : tree2)
                    .orElseThrow();
        }

        public Stream<Tree> visibleTrees(){
            return streamTrees().filter(tree -> !tree.visibleOnPaths().isEmpty());
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            for (int[] row : trees) {
                for (int i : row) {
                    sb.append(i);
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        @AllArgsConstructor
        @Getter
        public class Tree {
            private final int row;
            private final int col;

            public int getHeight() {
                return Grove.this.trees[row][col];
            }

            @Override
            public String toString(){
                return "Tree at (" + row + ", " + col + ") with height " + getHeight();
            }

            public boolean isTallerThan(Tree other) {
                return getHeight() > other.getHeight();
            }

            public long calcScenicScore(){
                return pathsToEdges().entrySet().stream()
                        .mapToLong(entry -> {
                            return entry.getValue().skip(1)
                                    .takeWhile(t -> this.getHeight() > t.getHeight() && t.getNeighbor(entry.getKey()) != null)
                                    .count() + 1;
                        })
                        .reduce(1, (a, b) -> a * b);
            }

            public Map<Direction, Stream<Tree>> pathsToEdges() {
                return Map.of(
                    Direction.UP, pathToEdge(Direction.UP),
                    Direction.DOWN, pathToEdge(Direction.DOWN),
                    Direction.LEFT, pathToEdge(Direction.LEFT),
                    Direction.RIGHT, pathToEdge(Direction.RIGHT)
                );
            }

            public Map<Direction, Stream<Tree>> visibleOnPaths() {
                return pathsToEdges().entrySet().stream()
                        .filter(entry -> {
                            return entry.getValue()
                                    .skip(1)
                                    .allMatch(other -> this.isTallerThan(other));
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }

            private Stream<Tree> pathToEdge(Direction direction) {
                return Stream.iterate(this, Objects::nonNull, tree -> tree.getNeighbor(direction));
            }

            public Tree getNeighbor(Direction direction) {
                int newRow = row + direction.delta[0];
                int newCol = col + direction.delta[1];
                if (newRow < 0 || newRow >= trees.length || newCol < 0 || newCol >= trees.length) {
                    return null;
                }
                return new Tree(newRow, newCol);
            }
        }
    }

    public enum Direction {
        UP(-1,0), DOWN(1,0), LEFT(0,-1), RIGHT(0,1);
        private final int[] delta;

        Direction(int... delta) {
            this.delta = delta;
        }
    }
}
