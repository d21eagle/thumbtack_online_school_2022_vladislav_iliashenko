package net.thumbtack.school.database.multithread.task18;
import net.thumbtack.school.database.multithread.task17.MultiStageTask;
import net.thumbtack.school.database.multithread.task16.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Writer extends Thread {
    private final BlockingQueue<MultiStageTask> tasksQueue;
    private final BlockingQueue<Action> actionQueue;
    private final int multiStageTask;
    private final Random random;

    public Writer(BlockingQueue<MultiStageTask> tasksQueue, BlockingQueue<Action> actionQueue, int multiStageTask, Random random) {
        this.tasksQueue = tasksQueue;
        this.actionQueue = actionQueue;
        this.multiStageTask = multiStageTask;
        this.random = random;
    }

    public void run() {
        actionQueue.add(new Action(ActionCondition.WRITER_START_WORK));
        boolean createNewDeveloper = random.nextBoolean();
        if (createNewDeveloper) {
            createNewDeveloper();
        } else {
            addTask();
        }
        actionQueue.add(new Action(ActionCondition.WRITER_FINISH_WORK));
    }

    public void createNewDeveloper() {
        Writer writer = new Writer(tasksQueue, actionQueue, multiStageTask, random);
        writer.start();
        try {
            writer.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void addTask() {
        for (int i = 0; i < multiStageTask; i++) {
            LinkedList<Executable> list = new LinkedList<>();
            for (int tasks = 1; tasks < 11; tasks++) {
                actionQueue.add(new Action(ActionCondition.TASK_CREATED));
                String data = String.format("Task%s by %s", tasks, Thread.currentThread().getName());
                list.add(new Task(data));
            }
            tasksQueue.add(new MultiStageTask("MultiTask", list));
        }
    }
}
