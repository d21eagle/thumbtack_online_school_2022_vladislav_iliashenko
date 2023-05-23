package net.thumbtack.school.database.multithread.task15;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Writer extends Thread {
    private static final Random random = new Random();
    private final Queue<Data> queue;
    private final int numData;

    public Writer(BlockingQueue<Data> queue, int numData) {
        this.queue = queue;
        this.numData = numData;
    }

    public void run() {
        for (int i = 0; i < numData; i++) {
            int[] arr = new int[200];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = random.nextInt(200);
            }
            queue.add(new Data(arr));
        }
    }
}
