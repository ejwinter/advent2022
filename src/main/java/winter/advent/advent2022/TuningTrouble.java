package winter.advent.advent2022;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TuningTrouble {

    @Getter @Setter @Accessors(chain = true)
    public static class Datastream {
        private final String content;

        private int minPacketMarkerSize = 4;
        private int minMessageMarkerSize = 14;

        public Datastream(String content) {
            this.content = content;
        }

        public static Datastream fromStream(InputStream content) {
            return new Datastream(String.join("", IOUtils.listAllLines(content)));
        }

        public Stream<Integer> findPacketMarkers(){
            return findStreamMarker(minPacketMarkerSize);
        }

        public Stream<Integer> findMessageMarkers(){
            return findStreamMarker(minMessageMarkerSize);
        }

        public Stream<Integer> findStreamMarker(int minWindowSize) {

            return IntStream.range(0, content.length())
                    .filter(i -> {
                        if(i < minWindowSize) {
                            return false;
                        }
                        return minWindowSize == IntStream.range(0, minWindowSize)
                                .mapToObj(j -> content.charAt(i-j))
                                .collect(Collectors.toSet())
                                .size();
                    })
                    .map(i -> i+1)
                    .boxed();
        }
    }
}
