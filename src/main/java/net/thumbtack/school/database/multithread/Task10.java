package net.thumbtack.school.database.multithread;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AddRemoveLock {
    private final List<Integer> list;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Random random = new Random();

    public AddRemoveLock(List<Integer> list) {
        this.list = list;
    }

    public void add() {
        for (int i = 0; i < 10_000; i++) {
            lock.lock();
            try {
                int num = random.nextInt(10_000);
                list.add(num);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void remove() {
        for (int i = 0; i < 10_000; i++) {
            lock.lock();
            try {
                while (list.isEmpty()) {
                    condition.await();
                }
                int num = random.nextInt(list.size());
                list.remove(num);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

class AddThreadLock extends Thread {
    private final AddRemoveLock addRemoveLock;

    public AddThreadLock(AddRemoveLock addRemoveLock) {
        this.addRemoveLock = addRemoveLock;
    }

    public void run() {
        addRemoveLock.add();
    }
}

class RemoveThreadLock extends Thread {
    private final AddRemoveLock addRemoveLock;

    public RemoveThreadLock(AddRemoveLock addRemoveLock) {
        this.addRemoveLock = addRemoveLock;
    }

    public void run() {
        addRemoveLock.remove();
    }
}

public class Task10 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        AddRemoveLock addRemoveLock = new AddRemoveLock(list);

        AddThreadLock addThreadLock = new AddThreadLock(addRemoveLock);
        RemoveThreadLock removeThreadLock = new RemoveThreadLock(addRemoveLock);
        addThreadLock.start();
        removeThreadLock.start();
        try {
            addThreadLock.join();
            removeThreadLock.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.printf("Размер списка: " + list.size());
    }
}
