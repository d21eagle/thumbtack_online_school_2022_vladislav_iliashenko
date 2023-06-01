package net.thumbtack.school.multithread.task17;
import net.thumbtack.school.multithread.task16.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import lombok.*;

@AllArgsConstructor
public class Writer implements Runnable {
    private String name;
    private Queue<MultiStageTask> taskQueue;
    private int tasksPerDeveloper;
    private CountDownLatch writerLatch;

    @Override
    public void run() {
        for (int i = 0; i < tasksPerDeveloper; i++) {
            MultiStageTask task = createTask();
            taskQueue.add(task);
            System.out.println(name + " добавляет задачу в очередь");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writerLatch.countDown();
    }

    private MultiStageTask createTask() {
        List<Executable> stages = new ArrayList<>();
        stages.add(() -> System.out.println(name + " выполняет стадию 1"));
        stages.add(() -> System.out.println(name + " выполняет стадию 2"));
        stages.add(() -> System.out.println(name + " выполняет стадию 3"));

        return new MultiStageTask("name", stages);
    }
}
