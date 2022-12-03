package winter.advent.advent2022;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MaxCal {

    public MaxCal(List<List<Integer>> elfRations) {
        this.elfRations = elfRations;
    }

    final List<List<Integer>> elfRations;

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


    private static MaxCal load(List<String> logLines){
        List<List<Integer>> logLinesByElf = new LinkedList<>();
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

    public static MaxCal load(InputStream inputStream) {
        return load(readLines(inputStream));
    }

    private static List<String> readLines(InputStream inputStream) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
