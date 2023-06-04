package net.thumbtack.school.multithread.task17;

import net.thumbtack.school.multithread.task16.Executable;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import lombok.*;
import net.thumbtack.school.multithread.task16.Task;


@AllArgsConstructor
public class Writer extends Thread {
    private BlockingQueue<MultiStageTask> taskQueue;
    private BlockingQueue<Event> eventQueue;
    private static final int MULTISTAGETASKS = ThreadLocalRandom.current().nextInt(1, 5);
    private static final int TASKS = ThreadLocalRandom.current().nextInt(1, 5);

    @Override
    public void run() {
        eventQueue.add(Event.WRITER_STARTED);
        for (int i = 0; i < MULTISTAGETASKS; i++) {
            LinkedList<Executable> list = new LinkedList<>();
            for (int j = 1; j < TASKS; j++) {
                eventQueue.add(Event.TASK_CREATED);
                String taskData = String.format("Task%s by %s", j, Thread.currentThread().getName());
                list.add(new Task(taskData));
            }
            taskQueue.add(new MultiStageTask("MultiTask", list));
        }
        eventQueue.add(Event.WRITER_FINISHED);
    }
}
