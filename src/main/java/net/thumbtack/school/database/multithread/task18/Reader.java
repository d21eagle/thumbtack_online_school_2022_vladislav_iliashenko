package net.thumbtack.school.database.multithread.task18;
import net.thumbtack.school.database.multithread.task17.MultiStageTask;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.List;
import net.thumbtack.school.database.multithread.task16.Executable;

public class Reader extends Thread {
    private final BlockingQueue<MultiStageTask> tasksQueue;
    private final BlockingQueue<Action> actionQueue;
    private final Random random;

    public Reader(BlockingQueue<MultiStageTask> tasksQueue, BlockingQueue<Action> actionQueue, Random random) {
        this.tasksQueue = tasksQueue;
        this.actionQueue = actionQueue;
        this.random = random;
    }

    public void run() {
        while (true) {
            try {
                MultiStageTask multiStageTask = tasksQueue.take();
                List<Executable> list = multiStageTask.getList();
                if (list == null) {
                    break;
                }
                if (!list.isEmpty()) {
                    int randInt = random.nextInt(list.size());
                    list.remove(randInt).execute();
                    actionQueue.put(new Action(ActionCondition.TASK_FINISHED));
                    if (!list.isEmpty()) {
                        tasksQueue.put(multiStageTask);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
