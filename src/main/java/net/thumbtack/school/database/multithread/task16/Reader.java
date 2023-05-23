package net.thumbtack.school.database.multithread.task16;
import java.util.concurrent.BlockingQueue;

public class Reader extends Thread {
    private final BlockingQueue<Task> queue;

    public Reader(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Task task = queue.take();
                if (task.getName() == null) {
                    break;
                }
                task.execute();
            }  catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
