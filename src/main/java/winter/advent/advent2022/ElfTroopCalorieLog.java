package winter.advent.advent2022;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static winter.advent.advent2022.IOUtils.listAllLines;

public class ElfTroopCalorieLog {

    final List<List<Integer>> elfRations;

    private ElfTroopCalorieLog(List<List<Integer>> elfRations) {
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
    public static ElfTroopCalorieLog loadFromLogLines(List<String> logLines){
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
        return new ElfTroopCalorieLog(logLinesByElf);
    }

    public static ElfTroopCalorieLog loadFromInputStream(InputStream logStream) {
        return loadFromLogLines(listAllLines(logStream));
    }
}
