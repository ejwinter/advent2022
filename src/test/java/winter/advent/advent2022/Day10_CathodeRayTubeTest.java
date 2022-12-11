package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day10_CathodeRayTubeTest {

    @Test
    void calcSignalStrength(){

        var device = new Day10_CathodeRayTube.Device();
        List<Day10_CathodeRayTube.Device.Cycle> cycles = device.issueCommand(IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/day10_cathodeRayTube_mid.txt"))).toList();

        assertEquals(20, cycles.get(19).getCycleNumber());
        assertEquals(21, cycles.get(19).getRegisterXValue());
        assertEquals(420, cycles.get(19).getSignalStrengthX());

        assertEquals(60, cycles.get(59).getCycleNumber());
        assertEquals(19, cycles.get(59).getRegisterXValue());

        assertEquals(220, cycles.get(219).getCycleNumber());
        //assertEquals(18, cycles.get(220).getRegisterValues().get("x"));

        //assertEquals(3960, cycles.get(220).getSignalStrength("x"));

        int sum = IntStream.iterate(19, i -> i < 220, i -> i + 40)
                .map(i -> cycles.get(i).getSignalStrengthX())
                .sum();

        assertEquals(13_140, sum);

    }

    @Test
    void calcSignalStrength_large(){

        var device = new Day10_CathodeRayTube.Device();
        List<Day10_CathodeRayTube.Device.Cycle> cycles = device.issueCommand(IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/day10_cathodeRayTube_large.txt"))).toList();

        //assertEquals(3960, cycles.get(220).getSignalStrength("x"));

        int sum = IntStream.iterate(19, i -> i < 220, i -> i + 40)
                .map(i -> cycles.get(i).getSignalStrengthX())
                .sum();

        assertEquals(17_840, sum);

    }

    @Test
    void renderAsImage_mid(){
        var device = new Day10_CathodeRayTube.Device();
        Stream<Day10_CathodeRayTube.Device.Cycle> cycles = device.issueCommand(IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/day10_cathodeRayTube_mid.txt")));
        var image = device.renderAsImage(cycles);
         assertEquals(6, image.length);
        assertEquals(40, image[0].length);

        Arrays.stream(image).map(String::new).forEach(System.out::println);

        var expected = """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....""";
        assertEquals(expected, Arrays.stream(image).map(String::new).collect(Collectors.joining("\n")));
    }

    @Test
    void renderAsImage_large() {
        var device = new Day10_CathodeRayTube.Device();
        Stream<Day10_CathodeRayTube.Device.Cycle> cycles = device.issueCommand(IOUtils.listAllLines(getClass().getResourceAsStream("/inputfiles/day10_cathodeRayTube_large.txt")));
        var image = device.renderAsImage(cycles);
        assertEquals(6, image.length);
        assertEquals(40, image[0].length);
        Arrays.stream(image).map(String::new).forEach(System.out::println);
        // I got it! boy that was tough to read!

    }

}