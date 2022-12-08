package winter.advent.advent2022;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NoSpaceLeftOnDevice {

    @Getter @Setter
    public static class Directory extends File {

        private static final Pattern COMMAND_PATTERN = Pattern.compile("\\$\\s+(\\S+)(\\s+(.+))?");
        private static final Pattern LISTING_PATTERN = Pattern.compile("(.+)\\s+(.+)");

        private static final Pattern FILE_LISTING_PATTERN = Pattern.compile("(\\d+)\\s+(.+)");

        private long size = 70_000_000;

        final Map<String, File> children = new TreeMap<>();

        public Directory(String name, Directory parent) {
            super(name, parent, 0);
            if(this.parent != null) {
                this.parent.children.put(name, this);
            }
        }

        public boolean isRoot(){
            return this.parent == null;
        }
        @Override
        public long getTotalSize() {
            return this.children.values().stream()
                    .mapToLong(File::getTotalSize)
                    .sum();
        }

        public long getUnusedSize() {
            return this.size - getTotalSize();
        }

        public Optional<Directory> findCleanupVictim(long spaceNeeded) {
            return findDirectoriesOfMinSize(spaceNeeded - getUnusedSize()).stream()
                    .min(Comparator.comparingLong(Directory::getTotalSize));
        }

        public Set<Directory> findDirectoriesOfMinSize(long minSize){
            return this.children.values().stream()
                    .filter(File::isDirectory)
                    .map(f -> (Directory) f)
                    .flatMap(d -> {
                        Set<Directory> subdir = new HashSet<>(d.findDirectoriesOfMinSize(minSize));
                        if(d.getTotalSize() >= minSize){
                            subdir.add(d);
                        }
                        return subdir.stream();
                    })
                    .collect(Collectors.toSet());
        }

        public Set<Directory> findDirectoriesOfMaxSize(long maxSize){
            return this.children.values().stream()
                    .filter(File::isDirectory)
                    .map(f -> (Directory) f)
                    .flatMap(d -> {
                        Set<Directory> subdir = new HashSet<>(d.findDirectoriesOfMaxSize(maxSize));
                        if(d.getTotalSize() <= maxSize){
                            subdir.add(d);
                        }
                        return subdir.stream();
                    })
                    .collect(Collectors.toSet());
        }

        @Override
        public boolean isDirectory(){
            return true;
        }

        public Directory addToListing(File f){
            this.children.put(f.name, f);
            return this;
        }

        public static Directory fromStream(InputStream content) {
            return fromListing(IOUtils.listAllLines(content));
        }

        public static Directory fromListing(Iterable<String> lines) {
            final Directory root = new Directory("/", null);
            Directory cwd = root;
            boolean listingmode = false;
            for(String line : lines){
                if(line.startsWith("$")){
                    listingmode = false;
                    Matcher matcher = COMMAND_PATTERN.matcher(line);
                    if(!matcher.matches()){
                        throw new IllegalArgumentException("Invalid command: " + line);
                    }
                    String command = matcher.group(1);
                    switch (command){
                        case "cd": cwd = changeDirectory(root, cwd, matcher.group(3)); break;
                        case "ls": listingmode = true; break;
                        default:
                            throw new IllegalArgumentException("Invalid command: " + line);
                    }
                }else if(listingmode){
                    Matcher listingMatcher = LISTING_PATTERN.matcher(line);
                    if(listingMatcher.matches()){
                        Matcher fileListingMatcher = FILE_LISTING_PATTERN.matcher(line);
                        String name = listingMatcher.group(2);
                        if(fileListingMatcher.matches()){
                            cwd.addToListing(new File(name, cwd, Long.parseLong(fileListingMatcher.group(1))));
                        }else{
                            cwd.addToListing(new Directory(name, cwd));
                        }
                    }else{
                        listingmode = false;
                    }
                } else{
                    throw new IllegalArgumentException("Invalid line: " + line);
                }
            }
            return root;
        }

        private static Directory changeDirectory(Directory root, Directory cwd, String path) {
            return switch (path) {
                case "." -> cwd;
                case ".." -> cwd.parent;
                case "/" -> root;
                default -> {
                    if(!cwd.children.containsKey(path)) {
                        throw new IllegalArgumentException("No such directory: " + path);
                    }
                    File possibleNewWd = cwd.children.get(path);
                    if(!possibleNewWd.isDirectory()){
                        throw new IllegalArgumentException("Is a regular file: " + path);
                    }
                    yield (Directory) possibleNewWd;
                }
            };
        }
    }

    @Getter @Setter
    public static class File {

        final protected String name;
        final protected Directory parent;

        final protected long size;

        public File(String name, Directory parent, long size) {
            this.name = name;
            this.parent = parent;
            this.size = size;
        }

        public long getTotalSize(){
            return this.size;
        }
        public boolean isDirectory(){
            return false;
        }

        public boolean isNormalFile(){
            return !isDirectory();
        }

        public String toString(){
            if(this.parent == null){
                return name;
            }
            return parent.name + "/" + name;
        }

    }
}
