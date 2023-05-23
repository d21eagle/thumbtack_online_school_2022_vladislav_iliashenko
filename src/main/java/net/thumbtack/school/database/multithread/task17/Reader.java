package net.thumbtack.school.database.multithread.task17;
import net.thumbtack.school.database.multithread.task16.Executable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Reader extends Thread {
    private final BlockingQueue<MultiStageTask> queue;
    private final Random random = new Random();
    private final ReaderData readerData;
    private final Lock lock;
    private final Condition threadsDone;

    public Reader(BlockingQueue<MultiStageTask> queue, ReaderData readerData, Lock lock, Condition threadsDone) {
        this.queue = queue;
        this.readerData = readerData;
        this.lock = lock;
        this.threadsDone = threadsDone;
    }

    public void run() {
        while (true) {
            try {
                try {
                    lock.lock();
                    threadsDone.signal();
                } finally {
                    lock.unlock();
                }
                MultiStageTask multiStageTask = queue.take();
                List<Executable> list = multiStageTask.getList();
                if (list == null) {
                    break;
                }
                if (!list.isEmpty()) {
                    Executable executable = list.remove(random.nextInt(list.size()));
                    executable.execute();
                    readerData.enlargeTasks();
                }
                if (!list.isEmpty()) {
                    queue.put(multiStageTask);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
