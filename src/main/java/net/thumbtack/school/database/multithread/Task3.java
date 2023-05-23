package net.thumbtack.school.database.multithread;

public class Task3 {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            long i = Thread.currentThread().getId();
            System.out.println("Thread: " + i);
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("Первичный поток ждет завершения всех потоков");
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Все потоки завершились, первичный поток продолжает работу");
    }
}
