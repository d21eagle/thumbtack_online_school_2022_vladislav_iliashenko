package net.thumbtack.school.database.multithread.task17;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task17 {
    public static void main(String[] args) throws Exception {
        int numReader = 5;
        int numWriter = 6;
        int multitasks = 1;

        Lock lock = new ReentrantLock();
        Condition threadsDone = lock.newCondition();
        ReaderData readerData = new ReaderData();
        WriterData writerData = new WriterData();

        BlockingQueue<MultiStageTask> queue = new LinkedBlockingQueue<>();
        Thread[] consumers = new Thread[numReader];
        for (int i = 0; i < numReader; i++) {
            consumers[i] = new Reader(queue, readerData, lock, threadsDone);
        }

        Thread[] producers = new Thread[numWriter];
        for (int i = 0; i < numWriter; i++) {
            producers[i] = new Writer(queue, multitasks, writerData);
        }

        startThreads(producers);
        startThreads(consumers);
        joinThreads(producers);

        while (writerData.getTasksCreated() != readerData.getTasksFinished()
                || writerData.getWriterThreadsCreated() != writerData.getWriterThreadsFinished()) {
            lock.lock();
            threadsDone.await();
            lock.unlock();
        }

        for (int i = 0; i < numReader; i++) {
            queue.add(new MultiStageTask("name", null));
        }
        joinThreads(consumers);
        System.out.println("Queue size: " + queue.size());
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
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
