package net.thumbtack.school.database.multithread.task17;

public class ReaderData {
    private int tasksFinished = 0;

    public synchronized void enlargeTasks() {
        tasksFinished++;
    }

    public int getTasksFinished() {
        return tasksFinished;
    }
}
