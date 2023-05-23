package net.thumbtack.school.database.multithread.task17;

public class WriterData {

    private int writerThreadsCreated = 0;
    private int writerThreadsFinished = 0;
    private int tasksCreated = 0;

    public synchronized void enlargeThreadsCreated() {
        writerThreadsCreated++;
    }

    public synchronized void enlargeTasksCreated() {
        tasksCreated++;
    }

    public synchronized void enlargeThreadsFinished() {
        writerThreadsFinished++;
    }

    public int getWriterThreadsCreated() {
        return writerThreadsCreated;
    }

    public int getWriterThreadsFinished() {
        return writerThreadsFinished;
    }

    public int getTasksCreated() {
        return tasksCreated;
    }
}
