package net.thumbtack.school.multithread.task17;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class Task17 {
    public static void main(String[] args) throws Exception {
        Queue<MultiStageTask> taskQueue = new ConcurrentLinkedQueue<>();
        int numWriters = 2;
        int tasksPerWriter = 5;

        CountDownLatch writerLatch = new CountDownLatch(numWriters);
        CountDownLatch readerLatch = new CountDownLatch(numWriters * tasksPerWriter);
        CountDownLatch stageLatch = new CountDownLatch(numWriters * tasksPerWriter);

        for (int i = 0; i < numWriters; i++) {
            Thread developerThread = new Thread(new Writer("Разработчик " + (i + 1), taskQueue, tasksPerWriter, writerLatch));
            developerThread.start();
        }

        for (int i = 0; i < numWriters * tasksPerWriter; i++) {
            Thread executorThread = new Thread(new Reader(taskQueue, writerLatch, readerLatch, stageLatch));
            executorThread.start();
        }

        stageLatch.await();
        System.out.println("Все стадийные задачи выполнены");

        readerLatch.await();
        System.out.println("Все задачи выполнены исполнителями");

        System.out.println("Размер очереди: " + taskQueue.size());
    }
}
