package winter.advent.advent2022;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor @Getter
public class Day10_CathodeRayTube {
    @NoArgsConstructor
    @Getter
    public static class Device {
        private final Register registerX = new Register(1);
        private int cycleCounter = 1;
        public Stream<Cycle> issueCommand(List<String> commands){
            return commands
                .stream()
                .flatMap(this::issueCommand);
        }
        public Stream<Cycle> issueCommand(String command){
            String[] split = command.split("\\s+");
            String operation = split[0];
            Cycle cycle1 = new Cycle(command);
            if(operation.startsWith("add")){
                Cycle cycle2 = new Cycle(command + " 2nd");
                int value = Integer.parseInt(split[1]);
                registerX.add(value);
                return Stream.of(cycle1, cycle2);
            }else if(operation.equals("noop")){
                return Stream.of(cycle1);
            }
            throw new IllegalArgumentException("Unknown operation: " + operation);
        }

        public char[][] renderAsImage(Stream<Cycle> cycles){
            final char[][] image = new char[6][40];
            for (char[] chars : image) {
                Arrays.fill(chars, '.');
            }

            cycles
                .forEach(cycle -> {
                    final int cycleNum = cycle.getCycleNumber();
                    int spriteCenter = cycle.registerXValue;
                    int row = (cycleNum-1) / 40;
                    int col = (cycleNum-1) % 40;

                    if(Math.abs(spriteCenter-col) <= 1){
                        image[row][col] = '#';
                    }
                });

            return image;
        }

        @AllArgsConstructor
        @Getter
        public class Cycle {

            private final int cycleNumber;

            private final int registerXValue;

            private final String command;

            public Cycle(String command) {
                this.command = command;
                cycleNumber = cycleCounter++;
                registerXValue = registerX.value;
            }

            public int getSignalStrengthX(){
                return registerXValue * cycleNumber;
            }
        }

        @Getter @Setter @AllArgsConstructor @NoArgsConstructor
        public class Register {
            private int value = 1;

            public Register add(int toAdd){
                value += toAdd;
                return this;
            }

        }
    }


}
