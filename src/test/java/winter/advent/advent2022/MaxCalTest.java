package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxCalTest {

        @Test
        void calculateMaxElfCals() {
            MaxCal maxCal = MaxCal.load(getClass().getResourceAsStream("/d1/d1-input.txt"));
            int actual = maxCal.calculateMaxElfCals();
            System.out.println("Max calories of an elf = " + actual);
            assertEquals(67622, actual);
        }

        @Test
        void calculateCalsOfTopThreeElves() {
            MaxCal maxCal = MaxCal.load(getClass().getResourceAsStream("/d1/d1-input.txt"));
            int actual = maxCal.calculateCalsOfTopThreeElves();
            System.out.println("Max calories of top 3 elves = " + actual);
            assertEquals(201491, actual);
        }
}