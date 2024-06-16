import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String source = "src\\imageBefore.jpg";
        String destination = "src\\imageAfter.jpg";
        String destinationThread = "src\\imageAfterThread.jpg";
        int britneyOffset = 100;

        long startTime = System.currentTimeMillis();
        ImageChanger change = new ImageChanger(source);
        change.changeBritney(britneyOffset);
        long endTime = System.currentTimeMillis();
        change.saveImage(destination);
        System.out.println("Czas wykonania bez wątków: " + (endTime - startTime) + "ms");

        ImageChanger threadChange = new ImageChanger(source);
        int numThreads = Runtime.getRuntime().availableProcessors();
        startTime = System.currentTimeMillis();
        threadChange.SheinFactoryWorkspace(britneyOffset, numThreads);
        endTime = System.currentTimeMillis();
        threadChange.saveImage(destinationThread);
        System.out.println("Czas wykonania z wątkami: " + (endTime - startTime) + "ms");
    }
}