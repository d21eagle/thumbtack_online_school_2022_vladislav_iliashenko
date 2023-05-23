package net.thumbtack.school.database.multithread;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Task8 {
    private static int i = 0;
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        Runnable writer = () -> {
            lock.writeLock().lock();
            try {
                i++;
                System.out.println("Писатель " + Thread.currentThread().getId() + " записывает значение " + i);
            } finally {
                lock.writeLock().unlock();
            }
        };

        Runnable reader = () -> {
            lock.readLock().lock();
            try {
                System.out.println("Читатель " + Thread.currentThread().getId() + " считывает значение " + i);
            } finally {
                lock.readLock().unlock();
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(reader).start();
            new Thread(writer).start();
            new Thread(reader).start();
            new Thread(writer).start();
        }
    }
}
