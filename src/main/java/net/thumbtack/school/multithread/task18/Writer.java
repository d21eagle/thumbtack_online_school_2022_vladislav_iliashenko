package net.thumbtack.school.multithread.task18;

import net.thumbtack.school.multithread.task16.Executable;
import net.thumbtack.school.multithread.task16.Task;
import net.thumbtack.school.multithread.task17.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import lombok.*;

@AllArgsConstructor
public class Writer extends Thread {
    private final BlockingQueue<MultiStageTask> tasksQueue;
    private final BlockingQueue<Event> eventQueue;
    private static final int MULTISTAGETASKS = ThreadLocalRandom.current().nextInt(1, 5);
    private static final int TASKS = ThreadLocalRandom.current().nextInt(1, 5);
    private static final Random random = new Random();

    public void run() {
        eventQueue.add(Event.WRITER_STARTED);
        boolean createNewDeveloper = random.nextBoolean();
        if (createNewDeveloper) {
            createNewDeveloper();
        } else {
            addTask();
        }
        eventQueue.add(Event.WRITER_FINISHED);
    }

    public void createNewDeveloper() {
        Writer writer = new Writer(tasksQueue, eventQueue);
        writer.start();
        try {
            writer.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void addTask() {
        for (int i = 0; i < MULTISTAGETASKS; i++) {
            LinkedList<Executable> list = new LinkedList<>();
            for (int j = 1; j < TASKS; j++) {
                eventQueue.add(Event.TASK_CREATED);
                String taskData = String.format("Task%s by %s", j, Thread.currentThread().getName());
                list.add(new Task(taskData));
            }
            tasksQueue.add(new MultiStageTask("MultiTask", list));
        }
    }
}
