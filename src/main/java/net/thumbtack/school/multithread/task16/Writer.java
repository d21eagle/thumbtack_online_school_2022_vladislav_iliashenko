package net.thumbtack.school.multithread.task16;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Writer extends Thread {
    private final Queue<Task> queue;
    private final int numData;

    public Writer(BlockingQueue<Task> queue, int numData) {
        this.queue = queue;
        this.numData = numData;
    }

    public void run() {
        for (int i = 0; i < numData; i++) {
            queue.add(new Task("Новая задача"));
        }
    }
}
