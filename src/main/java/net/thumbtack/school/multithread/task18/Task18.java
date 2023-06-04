package net.thumbtack.school.multithread.task18;

import net.thumbtack.school.multithread.task17.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task18 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<MultiStageTask> taskQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

        int writerCount = 5;
        int readerCount = 5;
        int tasksCreated = 0;
        int tasksFinished = 0;
        int writersCreated = 0;
        int writersFinished = 0;

        Thread[] writers = new Thread[writerCount];
        for (int i = 0; i < writerCount; i++) {
            writers[i] = new Writer(taskQueue, eventQueue);
        }

        Thread[] readers = new Thread[readerCount];
        for (int i = 0; i < readerCount; i++) {
            readers[i] = new Reader(taskQueue, eventQueue);
        }

        for (Thread writer : writers) {
            writer.start();
        }

        for (Thread reader : readers) {
            reader.start();
        }

        for (Thread writer : writers) {
            try {
                writer.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        while (!(tasksCreated == tasksFinished && writersCreated == writersFinished) || tasksCreated == 0) {
            Event event = eventQueue.take();
            if (event.equals(Event.TASK_CREATED)) {
                tasksCreated++;
            }
            if (event.equals(Event.TASK_FINISHED)) {
                tasksFinished++;
            }
            if (event.equals(Event.WRITER_STARTED)) {
                writersCreated++;
            }
            if (event.equals(Event.WRITER_FINISHED)) {
                writersFinished++;
            }
        }

        for (int i = 0; i < readerCount; i++) {
            taskQueue.add(new MultiStageTask("name", null));
        }

        for (Thread reader : readers) {
            try {
                reader.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Queue size: " + taskQueue.size());
    }
}
