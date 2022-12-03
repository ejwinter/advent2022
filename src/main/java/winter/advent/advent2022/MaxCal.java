package winter.advent.advent2022;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MaxCal {

    final List<List<Integer>> elfRations;

    private MaxCal(List<List<Integer>> elfRations) {
        this.elfRations = elfRations;
    }

    public int calculateMaxElfCals() {
        return elfRations.stream()
                .map(oneElfRations -> {
                    return oneElfRations.stream()
                            .mapToInt(Integer::intValue)
                            .sum();
                }).max(Integer::compareTo).orElse(0);
    }

    public int calculateCalsOfTopThreeElves(){
        return elfRations.stream()
                .map(oneElfRations -> {
                    return oneElfRations.stream()
                            .mapToInt(Integer::intValue)
                            .sum();
                }).sorted(Integer::compareTo)
                .skip(elfRations.size() - 3)
                .mapToInt(Integer::intValue).sum();
    }

    /**
     * Loads from the lines of a ration calorie log where:
     *   - each line is an integer representing the calories of a ration for an elf
     *   - different elves are seperated by a blank line
     * @param logLines the lines for the ration log
     * @return a MaxCal object that can be used to calculate using the ration log
     */
    public static MaxCal loadFromLogLines(List<String> logLines){
        final List<List<Integer>> logLinesByElf = new LinkedList<>();
        List<Integer> currentElf = new LinkedList<>();
        for (String logLine : logLines) {
            if(logLine.trim().isEmpty()) {
                if(!currentElf.isEmpty()) {
                    logLinesByElf.add(currentElf);
                    currentElf = new LinkedList<>();
                }
            }else{
                currentElf.add(Integer.parseInt(logLine));
            }
        }
        if(!currentElf.isEmpty()) {
            logLinesByElf.add(currentElf);
        }
        return new MaxCal(logLinesByElf);
    }

    public static MaxCal loadFromInputStream(InputStream logStream) {
        return loadFromLogLines(readAllLines(logStream));
    }

    private static List<String> readAllLines(InputStream inputStream) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
