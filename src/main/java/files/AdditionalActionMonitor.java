package files;

import java.nio.file.Path;


/**
 * While navigating file tree,
 * also call methods from this interface,
 * for the lulz.
 */
public interface AdditionalActionMonitor {

    default void preVisitAction(Path dir) {
        System.out.println("I am goind in this directory: " + dir);
    }

    default void postVisitAction(Path dir) {
        System.out.println("I have just left this directory:" + dir);
    }

}
