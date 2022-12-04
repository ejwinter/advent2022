package winter.advent.advent2022;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class CampCleanup {

    private final List<Pairing> pairings;

    public CampCleanup(Pairing... pairings) {
        this(List.of(pairings));
    }

    public Set<ElfAssignments> getRedundentElfAssignments() {
        return pairings.stream()
                .map(pairing -> pairing.fullyRedundant())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    public Set<Pairing> getOverlappingPairings() {
        return pairings.stream()
                .filter(Pairing::hasOverlap)
                .collect(Collectors.toSet());
    }

    public static CampCleanup loadFromInputStream(InputStream inputStream) {
        List<Pairing> pairings = readAllLines(inputStream).stream()
                .map(Pairing::fromString)
                .collect(Collectors.toList());
        return new CampCleanup(pairings);
    }

    @AllArgsConstructor
    @Getter
    public static class ElfAssignments {
        final Set<Integer> assignments;

        public static ElfAssignments forAssignmentRange(String assignmentRange) {
            String[] split = assignmentRange.split("-");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            return IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.collectingAndThen(Collectors.toSet(), ElfAssignments::new));
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Pairing {
        final ElfAssignments elf1;
        final ElfAssignments elf2;

        public Optional<ElfAssignments> fullyRedundant(){
            if (elf1.getAssignments().containsAll(elf2.getAssignments())) {
                return Optional.of(elf1);
            } else if (elf2.getAssignments().containsAll(elf1.getAssignments())) {
                return Optional.of(elf2);
            } else {
                return Optional.empty();
            }
        }

        public static Pairing fromString(String s) {
            String[] split = s.split(",");
            return new Pairing(ElfAssignments.forAssignmentRange(split[0]), ElfAssignments.forAssignmentRange(split[1]));
        }

        public boolean hasOverlap() {
            return elf1.assignments.stream().anyMatch(elf2.assignments::contains);
        }
    }


    private static List<String> readAllLines(InputStream inputStream) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
