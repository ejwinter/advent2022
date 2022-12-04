package winter.advent.advent2022;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
public class RucksackReorg {

    private final List<Rucksack> rucksacks;

    public RucksackReorg(List<Rucksack> rucksacks) {
        this.rucksacks = rucksacks;
    }

    public RucksackReorg(Rucksack... rucksacks) {
        this.rucksacks = Arrays.asList(rucksacks);
    }

    public static RucksackReorg loadFromInputStream(InputStream inputStream){
        List<Rucksack> rucksacks = readAllLines(inputStream).stream()
                .map(Rucksack::fromString)
                .collect(Collectors.toList());
        return new RucksackReorg(rucksacks);
    }

    public int getTotalPriority() {
        return this.rucksacks.stream()
                .mapToInt(Rucksack::getTotalPriority)
                .sum();
    }

    @AllArgsConstructor
    @Getter
    public static class Rucksack {
        private final Compartment compartmentA;
        private final Compartment compartmentB;

        public static Rucksack fromString(String rucksackString){
            return new Rucksack(
                new Compartment(rucksackString.substring(0, rucksackString.length()/2)),
                new Compartment(rucksackString.substring(rucksackString.length()/2))
            );
        }

        public Set<Character> findCommmonItems() {
            return compartmentA.getItems().codePoints()
                    .mapToObj(i -> (char) i)
                    .filter(c -> compartmentB.getItems().contains(String.valueOf(c)))
                    .collect(Collectors.toSet());
        }

        public int getTotalPriority(){
            return this.findCommmonItems().stream()
                    .map(Rucksack::priority)
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        public static int priority(char c){
            if(c > 91){
                return c - 96;
            }else{
                return c - 64 + 26;
            }
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Compartment{
        private final String items;

    }

    private static List<String> readAllLines(InputStream inputStream) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
