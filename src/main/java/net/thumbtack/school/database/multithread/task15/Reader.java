package net.thumbtack.school.database.multithread.task15;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class Reader extends Thread {
    private final BlockingQueue<Data> queue;

    public Reader(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Data data = queue.take();
                int[] arr = data.getData();
                if (arr == null) {
                    break;
                }
                System.out.println(Arrays.toString(arr));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
