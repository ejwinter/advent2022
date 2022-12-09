package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NoSpaceLeftOnDeviceTest {


    @Test
    void fromListing(){

        NoSpaceLeftOnDevice.Directory root = NoSpaceLeftOnDevice.Directory.fromStream(getClass().getResourceAsStream("/inputfiles/short-listing.txt"));
        assertEquals(48_381_165, root.getTotalSize());
        Set<NoSpaceLeftOnDevice.Directory> directoriesOfMaxSize = root.findDirectoriesOfMaxSize(100_000);
        assertEquals(2, directoriesOfMaxSize.size());
        assertEquals(95_437, directoriesOfMaxSize.stream().map(f -> f.getTotalSize()).reduce(Long::sum).get());

        assertEquals(24_933_642, root.findCleanupVictim(30_000_000).get().getTotalSize());
    }

    @Test
    void fromListing_long(){

        NoSpaceLeftOnDevice.Directory root = NoSpaceLeftOnDevice.Directory.fromStream(getClass().getResourceAsStream("/inputfiles/long-listing.txt"));
        assertEquals(43_629_016, root.getTotalSize());
        Set<NoSpaceLeftOnDevice.Directory> directoriesOfMaxSize = root.findDirectoriesOfMaxSize(100_000);
        assertEquals(40, directoriesOfMaxSize.size());
        assertEquals(1_845_346, directoriesOfMaxSize.stream().map(f -> f.getTotalSize()).reduce(Long::sum).get());
        assertEquals(3_636_703, root.findCleanupVictim(30_000_000).get().getTotalSize());
    }
}