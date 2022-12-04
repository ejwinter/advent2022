package winter.advent.advent2022;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
public class RucksackReorg {

    private final List<Rucksack> rucksacks;

    private final List<Group> groups;

    public RucksackReorg(List<Rucksack> rucksacks) {
        this.rucksacks = rucksacks;
        this.groups = new ArrayList<>(rucksacks.size() / 3);
        List<Rucksack> currentGroup = new ArrayList<>(3);
        for (Rucksack rucksack : rucksacks) {
            currentGroup.add(rucksack);
            if (currentGroup.size() == 3) {
                groups.add(new Group(currentGroup));
                currentGroup = new ArrayList<>(3);
            }
        }
    }

    public RucksackReorg(Rucksack... rucksacks) {
        this(Arrays.asList(rucksacks));
    }

    public static RucksackReorg loadFromInputStream(InputStream inputStream) {
        List<Rucksack> rucksacks = readAllLines(inputStream).stream()
                .map(Rucksack::fromString)
                .collect(Collectors.toList());
        return new RucksackReorg(rucksacks);
    }

    public int getGroupPriority() {
        return groups.stream().mapToInt(Group::getTotalPriority).sum();
    }

    public int getTotalPriority() {
        return this.rucksacks.stream()
                .mapToInt(Rucksack::getTotalPriority)
                .sum();
    }

    /**
     * Represents a group of 3 rucksacks that must contain a common item type to get a  badge.
     */
    @AllArgsConstructor
    @Getter
    public static class Group {
        private final List<Rucksack> rucksacks;

        public int getTotalPriority() {
            final Character badge = this.getBadge();
            if(badge == null) {
                return 0;
            }
            return priority(badge);
        }

        public Character getBadge() {
            if (rucksacks.isEmpty()) {
                return null;
            }
            return rucksacks.get(0).allItems().codePoints()
                    .mapToObj(i -> (char) i)
                    .filter(c -> {
                        return rucksacks.stream()
                                .skip(1)
                                .allMatch(r -> r.allItems().indexOf(c) >= 0);
                    })
                    .findFirst().orElse(null);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Rucksack {
        private final Compartment compartmentA;
        private final Compartment compartmentB;

        public static Rucksack fromString(String rucksackString) {
            return new Rucksack(
                    new Compartment(rucksackString.substring(0, rucksackString.length() / 2)),
                    new Compartment(rucksackString.substring(rucksackString.length() / 2))
            );
        }

        public Set<Character> findCommmonItems() {
            return compartmentA.getItems().codePoints()
                    .mapToObj(i -> (char) i)
                    .filter(c -> compartmentB.getItems().contains(String.valueOf(c)))
                    .collect(Collectors.toSet());
        }

        public int getTotalPriority() {
            return this.findCommmonItems().stream()
                    .map(RucksackReorg::priority)
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        public String allItems() {
            return compartmentA.getItems() + compartmentB.getItems();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Compartment {
        private final String items;

    }

    private static List<String> readAllLines(InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static int priority(char c) {
        if (c > 91) {
            return c - 96;
        } else {
            return c - 64 + 26;
        }
    }
}
