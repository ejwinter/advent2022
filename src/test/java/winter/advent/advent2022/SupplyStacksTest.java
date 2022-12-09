package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SupplyStacksTest {

    @Test
    void loadFromInputStream(){
        SupplyStacks supplyStacks = SupplyStacks.loadFromInputStream(getClass().getResourceAsStream("/inputfiles/supply-stacks.txt"));
        assertEquals(9, supplyStacks.getShip().getStacks().size());
        assertEquals("9: TFBNQLH", supplyStacks.getShip().getStacks().get(8).toString());
        assertEquals("WQJBBLMJT", supplyStacks.getShip().getAllTopCrates().stream().map(SupplyStacks.Crate::toString).collect(Collectors.joining("")));
    }

    @Test
    void runMovementLog(){
        SupplyStacks supplyStacks = SupplyStacks.loadFromInputStream(getClass().getResourceAsStream("/inputfiles/supply-stacks.txt"));
        supplyStacks.getShip().runMovementLog();
        assertEquals(9, supplyStacks.getShip().getStacks().size());
        assertEquals("9: BH", supplyStacks.getShip().getStacks().get(8).toString());
        assertEquals("RLFNRTNFB", supplyStacks.getShip().getAllTopCrates().stream().map(SupplyStacks.Crate::toString).collect(Collectors.joining("")));
    }

    @Test
    void runMovementLog_multilift(){
        SupplyStacks supplyStacks = SupplyStacks.loadFromInputStream(getClass().getResourceAsStream("/inputfiles/supply-stacks.txt"));
        System.out.println("Before:\n" + supplyStacks.getShip());
        supplyStacks.getShip().setMultilift(true).runMovementLog();
        assertEquals(9, supplyStacks.getShip().getStacks().size());
        assertEquals("9: BC", supplyStacks.getShip().getStacks().get(8).toString());
        assertEquals("MHQTLJRLB", supplyStacks.getShip().getAllTopCrates().stream().map(SupplyStacks.Crate::toString).collect(Collectors.joining("")));
        System.out.println("After:\n" + supplyStacks.getShip());
    }
}