package net.thumbtack.school.multithread.task16;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task16 {
    public static void main(String[] args) {
        int numReaders = 10;
        int numWriters = 10;
        int tasks = 2;

        BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

        Thread[] readers = new Thread[numReaders];
        for (int i = 0; i < numReaders; i++) {
            readers[i] = new Reader(queue);
        }

        Thread[] writers = new Thread[numWriters];
        for (int i = 0; i < numWriters; i++) {
            writers[i] = new Writer(queue, tasks);
        }

        startThreads(writers);
        startThreads(readers);
        joinThreads(writers);

        for (int i = 0; i < numReaders; i++) {
            queue.add(new Task(null));
        }

        joinThreads(readers);
        System.out.println("Размер очереди: " + queue.size());
    }

    public static void startThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void joinThreads(Thread[] threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
