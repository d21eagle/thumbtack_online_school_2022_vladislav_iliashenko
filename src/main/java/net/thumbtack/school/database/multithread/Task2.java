package net.thumbtack.school.database.multithread;

public class Task2 {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Новый поток начал работу");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Новый поток закончил работу");
        });

        thread.start();

        System.out.println("Первичный поток ждет завершения нового потока");
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Новый поток завершился, первичный поток продолжает работу");
    }
}
