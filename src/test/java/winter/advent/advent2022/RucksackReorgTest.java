package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RucksackReorgTest {

    @Test
    void createRucksack(){
        RucksackReorg.Rucksack rucksack = RucksackReorg.Rucksack.fromString("abcDQa");
        assertEquals("abc", rucksack.getCompartmentA().getItems());
        assertEquals("DQa", rucksack.getCompartmentB().getItems());
        assertEquals(Set.of('a'), rucksack.findCommmonItems());
    }

    @Test
    void smallSet(){
        RucksackReorg rucksackReorg = new RucksackReorg(
                RucksackReorg.Rucksack.fromString("vJrwpWtwJgWrhcsFMMfFFhFp"),
                RucksackReorg.Rucksack.fromString("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"),
                RucksackReorg.Rucksack.fromString("PmmdzqPrVvPwwTWBwg"),
                RucksackReorg.Rucksack.fromString("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"),
                RucksackReorg.Rucksack.fromString("ttgJtRGJQctTZtZT"),
                RucksackReorg.Rucksack.fromString("CrZsJsPPZsGzwwsLwLmpwMDw")
        );

        assertEquals(Set.of('p'), rucksackReorg.getRucksacks().get(0).findCommmonItems());
        assertEquals(16, rucksackReorg.getRucksacks().get(0).getTotalPriority());
        assertEquals(Set.of('L'), rucksackReorg.getRucksacks().get(1).findCommmonItems());
        assertEquals(38, rucksackReorg.getRucksacks().get(1).getTotalPriority());
        assertEquals(157, rucksackReorg.getTotalPriority());
    }

    @Test
    void realTest() {
        RucksackReorg rucksackReorg = RucksackReorg.loadFromInputStream(getClass().getResourceAsStream("/d3/rucksack-reorg.txt"));
        assertEquals(7889, rucksackReorg.getTotalPriority());
    }

}