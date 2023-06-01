package net.thumbtack.school.multithread.task17;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import lombok.*;

@AllArgsConstructor
public class Reader extends Thread {
    private Queue<MultiStageTask> taskQueue;
    private CountDownLatch writerLatch;
    private CountDownLatch readerLatch;
    private CountDownLatch stageLatch;

    @Override
    public void run() {
        while (true) {
            MultiStageTask task = null;

            synchronized (taskQueue) {
                if (!taskQueue.isEmpty()) {
                    task = taskQueue.poll();
                }
            }

            if (task != null) {
                task.execute();

                if (task.getStages().size() > task.getCurrentStage()) {
                    synchronized (taskQueue) {
                        taskQueue.add(task);
                    }
                    stageLatch.countDown();
                } else {
                    System.out.println("Задача выполнена");
                    readerLatch.countDown();
                }
            } else {
                if (writerLatch.getCount() == 0) {
                    break;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
