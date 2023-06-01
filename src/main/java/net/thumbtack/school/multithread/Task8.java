package net.thumbtack.school.multithread;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Task8 {
    private static int i = 0;
    private static Semaphore write = new Semaphore(1);
    private static Semaphore read = new Semaphore(0);

    public static void main(String[] args) {
        Runnable writer = () -> {
            try {
                write.acquire();
                i++;
                System.out.println("Писатель " + Thread.currentThread().getId() + " записывает значение " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                read.release();
            }
        };

        Runnable reader = () -> {
            try {
                read.acquire();
                System.out.println("Читатель " + Thread.currentThread().getId() + " считывает значение " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                write.release();
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
