package files;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Walk file tree,
 * find all files with certain extensions.
 * <p>
 * Read each file,
 * extract java class name from it,
 * save it to a list,
 * and then return that list.
 */
public class MyFileSearcher {

    AdditionalActionMonitor myMonitor;

    private Path pathToSearch;
    private String[] extensions;

    private Set<String> foundJavaClasses = new HashSet<>();

    public MyFileSearcher(AdditionalActionMonitor myMonitor, String pathToSearch, String... extensions) {
        this.myMonitor = myMonitor;
        this.pathToSearch = Paths.get(pathToSearch);
        this.extensions = extensions;
    }

    public Set<String> getFoundJavaClasses() {

        try {
            Files.walkFileTree(pathToSearch, new MyFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return foundJavaClasses;
    }

    private class MyFileVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

            // call additional actions
            myMonitor.preVisitAction(dir);

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

            // navigate each file, check if extension matches
            for (String validExtension : extensions) {
                if (file.toString().endsWith("." + validExtension)) {
                    // valid file
                    // now read file with buffered reader
                    // grab line with java class name

                    // use this to automatically clean resources
                    // (no need to close buffer later)
                    try (BufferedReader bfr =
                                 new BufferedReader(
                                         new InputStreamReader(
                                                 new BufferedInputStream(
                                                         new FileInputStream(file.toString())
                                                 ), "UTF-8"
                                         )
                                 )
                    ) {
                        String line;
                        String searchRegex = "(.*class\\s)([A-Z][a-zA-Z0-9]+)([ie<{ ].*)";
                        Pattern pattern = Pattern.compile(searchRegex);

                        while ((line = bfr.readLine()) != null) {

                            if (line.matches(searchRegex)) {

                                Matcher matcher = pattern.matcher(line);
                                if (matcher.find()) {
                                    foundJavaClasses.add(matcher.group(2));
                                }

                            }
                        }

                    }

                }
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

            // call additional actions
            myMonitor.postVisitAction(dir);

            return FileVisitResult.CONTINUE;
        }

    }

}
