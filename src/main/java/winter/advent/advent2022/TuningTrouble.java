package winter.advent.advent2022;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TuningTrouble {

    @AllArgsConstructor @Getter @Setter @Accessors(chain = true)
    public static class Datastream {
        private final String content;

        private int minStartSize = 4;

        public Datastream(String content) {
            this.content = content;
        }

        public static Datastream fromStream(InputStream content) {
            return new Datastream(String.join("", IOUtils.listAllLines(content)));
        }

        public Stream<Integer> findStartOfPacketIndices() {

            return IntStream.range(0, content.length())
                    .filter(i -> {
                        if(i < minStartSize) {
                            return false;
                        }
                        return minStartSize == IntStream.range(0, minStartSize)
                                .mapToObj(j -> content.charAt(i-j))
                                .collect(Collectors.toSet())
                                .size();
                    })
                    .map(i -> i+1)
                    .boxed();
        }
    }
}
