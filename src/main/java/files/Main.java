package files;

public class Main {

    public static void main(String[] args) {

        AdditionalActionMonitor myMonitor = new AdditionalActionMonitor() {
            // no need to add anything here because of default methods
            // which are already implemented
        };

        MyFileSearcher mySearcher = new MyFileSearcher(myMonitor, "./", "java");

        mySearcher.getFoundJavaClasses().forEach(c -> System.out.println("Class: " + c));

    }

}
