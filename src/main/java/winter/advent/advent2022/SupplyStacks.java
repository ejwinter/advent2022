package winter.advent.advent2022;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor @Getter
public class SupplyStacks {

    private final Ship ship;

    public static SupplyStacks loadFromInputStream(InputStream inputStream) {
        List<String> lines = IOUtils.listAllLines(inputStream);
        List<String> initialOrientationLines = lines.stream()
                .takeWhile(line -> !line.trim().isBlank())
                .toList();

        List<CrateStack> crateStacks = Arrays.stream(initialOrientationLines.get(initialOrientationLines.size() - 1).split(" "))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .map(CrateStack::new)
                .toList();

        IntStream.rangeClosed(0, crateStacks.size()-1)
                .forEach(i -> {
                    initialOrientationLines.stream()
                            .limit(initialOrientationLines.size() - 1)
                            .map(line -> {
                                int crateLocation = 1+(i*4);
                                return crateLocation < line.length() ? line.charAt(crateLocation) : ' ';
                            })
                            .filter(c -> c != ' ')
                            .map(c -> new Crate(c))
                            .forEach(c -> crateStacks.get(i).add(c));
                });

        List<Movement> movementLog = lines.stream()
                .filter(line -> line.startsWith("move "))
                .map(line -> Pattern.compile("move (\\d+) from (\\d+) to (\\d+)").matcher(line))
                .filter(Matcher::matches)
                .map(matcher -> new Movement(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))))
                .toList();

        return new SupplyStacks(new Ship(crateStacks, movementLog));
    }


    @AllArgsConstructor @Getter @Setter @Accessors(chain = true)
    public static class Ship {

        private boolean multilift = false;

        private boolean debug = false;

        private final List<CrateStack> stacks;

        private final List<Movement> movementLog;

        public Ship(List<CrateStack> stacks, List<Movement> movementLog) {
            this.stacks = stacks;
            this.movementLog = movementLog;
        }

        public void move(boolean hasMultiLift, int numberOfCratesToMove, int fromStack, int toStack) {
            CrateStack from = stacks.get(fromStack-1);
            CrateStack to = stacks.get(toStack-1);
            from.moveTo(hasMultiLift, numberOfCratesToMove, to);
        }

        public List<Crate> getAllTopCrates(){
            return this.stacks.stream()
                    .map(CrateStack::getTopCrate)
                    .toList();
        }

        @Override
        public String toString() {
            return this.stacks.stream()
                    .map(CrateStack::toString)
                    .collect(Collectors.joining("\n"));
        }


        public void runMovementLog() {
            if(movementLog != null){
                movementLog.forEach(movement -> {
                    move(multilift, movement.getNumberOfCratesToMove(), movement.getFromStack(), movement.getToStack());
                    if(debug) {
                        System.out.println("\n" + this);
                    }
                });
            }
        }
    }

    @AllArgsConstructor @Getter
    public static class CrateStack {
        private final int index;
        private final List<Crate> crates = new LinkedList<>();

        public void moveTo(boolean hasMultiLift, int numberOfCratesToMove, CrateStack to) {
            List<Crate> removed = new ArrayList<>(numberOfCratesToMove);
            for (int i = 0; i < numberOfCratesToMove; i++) {
                if (!crates.isEmpty()) {
                    removed.add(crates.remove(0));
                }else{
                    System.out.println("No crates to move");
                }
            }
            if(hasMultiLift) {
                Collections.reverse(removed);
            }
            for (int i = 0; i < removed.size(); i++) {
                to.crates.add(0, removed.get(i));
            }

        }

        public void add(Crate crate){
            crates.add(crate);
        }

        public Crate getTopCrate(){
            if(crates.isEmpty()){
                return null;
            }
            return crates.get(0);
        }

        @Override
        public String toString() {
            return "" + index + ": " + crates.stream().map(Crate::toString).reduce((a, b) -> a + b).orElse("");
        }
    }

    @AllArgsConstructor @Getter
    public static class Crate {
        private final char type;

        @Override
        public String toString() {
            return String.valueOf(type);
        }
    }

    @AllArgsConstructor @Getter
    public static class Movement {
        private final int numberOfCratesToMove;
        private final int fromStack;
        private final int toStack;
    }
}
