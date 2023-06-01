package net.thumbtack.school.multithread;
import java.util.ArrayList;
import java.util.Random;

class AddThread extends Thread {
    private ArrayList<Integer> list;

    public AddThread(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10_000; i++) {
            synchronized (list) {
                list.add(random.nextInt(10_000));
            }
        }
    }
}

class RemoveThread extends Thread {
    private ArrayList<Integer> list;

    public RemoveThread(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            synchronized (list) {
                if (!list.isEmpty()) {
                    list.remove(random.nextInt(list.size()));
                }
            }
        }
    }
}

public class Task4 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(10_000);
        Thread addThread = new Thread(new AddThread(list));
        Thread removeThread = new Thread(new RemoveThread(list));

        addThread.start();
        removeThread.start();
        try {
            addThread.join();
            removeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Размер списка: " + list.size());
    }
}
