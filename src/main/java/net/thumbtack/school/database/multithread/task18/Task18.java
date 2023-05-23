package net.thumbtack.school.database.multithread.task18;
import net.thumbtack.school.database.multithread.task17.MultiStageTask;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task18 {
    public static void main(String[] args) throws InterruptedException{
        int consumerCount = 5;
        int producerCount = 5;
        int tasksProducing = 2;

        Random random = new Random();
        BlockingQueue<MultiStageTask> tasksQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Action> eventsQueue = new LinkedBlockingQueue<>();

        int tasksCreated = 0;
        int tasksFinished = 0;
        int producersCreated = 0;
        int producersFinished = 0;

        Thread[] consumers = new Thread[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new Reader(tasksQueue, eventsQueue, random);
        }

        Thread[] producers = new Thread[producerCount];
        for (int i = 0; i < producerCount; i++) {
            producers[i] = new Writer(tasksQueue, eventsQueue, tasksProducing, random);
        }

        startThreads(producers);
        startThreads(consumers);
        joinThreads(producers);

        while (!(tasksCreated == tasksFinished && producersCreated == producersFinished)
                || tasksCreated == 0) {
            Action action = eventsQueue.take();
            if (action.getType().equals(ActionCondition.TASK_CREATED)) {
                tasksCreated++;
            }
            if (action.getType().equals(ActionCondition.TASK_FINISHED)) {
                tasksFinished++;
            }
            if (action.getType().equals(ActionCondition.WRITER_START_WORK)) {
                producersCreated++;
            }
            if (action.getType().equals(ActionCondition.WRITER_FINISH_WORK)) {
                producersFinished++;
            }
        }

        for (int i = 0; i < consumerCount; i++) {
            tasksQueue.add(new MultiStageTask("name", null));
        }
        joinThreads(consumers);
        System.out.println("Queue size: " + tasksQueue.size());
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
