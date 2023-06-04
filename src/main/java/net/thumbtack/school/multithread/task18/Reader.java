package net.thumbtack.school.multithread.task18;

import net.thumbtack.school.multithread.task17.*;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.List;
import net.thumbtack.school.multithread.task16.Executable;
import lombok.*;

@AllArgsConstructor
public class Reader extends Thread {
    private final BlockingQueue<MultiStageTask> tasksQueue;
    private final BlockingQueue<Event> eventQueue;
    private static final Random random = new Random();

    public void run() {
        while (true) {
            try {
                MultiStageTask multiStageTask = tasksQueue.take();
                List<Executable> tasks = multiStageTask.getStages();
                if (tasks == null) {
                    break;
                }
                if (!tasks.isEmpty()) {
                    tasks.remove(random.nextInt(tasks.size())).execute();
                    eventQueue.put(Event.TASK_FINISHED);
                    if (!tasks.isEmpty()) {
                        tasksQueue.put(multiStageTask);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
