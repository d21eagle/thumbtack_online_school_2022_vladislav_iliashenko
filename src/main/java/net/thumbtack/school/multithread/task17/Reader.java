package net.thumbtack.school.multithread.task17;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import lombok.*;
import net.thumbtack.school.multithread.task16.Executable;

@AllArgsConstructor
public class Reader extends Thread {
    private final BlockingQueue<MultiStageTask> taskQueue;
    private final BlockingQueue<Event> eventQueue;
    private static final Random RANDOM = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                MultiStageTask multiStageTask = taskQueue.take();
                List<Executable> tasks = multiStageTask.getStages();
                if (tasks == null) {
                    break;
                }
                if (!tasks.isEmpty()) {
                    tasks.remove(RANDOM.nextInt(tasks.size())).execute();
                    eventQueue.put(Event.TASK_FINISHED);
                    if (!tasks.isEmpty()) {
                        taskQueue.put(multiStageTask);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
