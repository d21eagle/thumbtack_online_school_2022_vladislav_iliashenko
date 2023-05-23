package net.thumbtack.school.database.multithread.task17;
import net.thumbtack.school.database.multithread.task16.Executable;
import net.thumbtack.school.database.multithread.task16.Task;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Writer extends Thread {
    private final Queue<MultiStageTask> queue;
    private final int multitask;
    private final WriterData writerData;

    public Writer(BlockingQueue<MultiStageTask> queue, int multitask, WriterData writerData) {
        this.queue = queue;
        this.multitask = multitask;
        this.writerData = writerData;
    }

    public void run() {
        writerData.enlargeThreadsCreated();
        for(int i = 0; i < multitask; i++) {
            LinkedList<Executable> list = new LinkedList<>();
            for (int tasks = 1; tasks < 16; tasks++) {
                writerData.enlargeTasksCreated();
                String data = String.format("Task%s by %s", tasks, Thread.currentThread().getName());
                list.add(new Task(data));
            }
            queue.add(new MultiStageTask("MultiStageTask", list));
        }
        writerData.enlargeThreadsFinished();
    }
}
