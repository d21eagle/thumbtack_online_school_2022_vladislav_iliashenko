package net.thumbtack.school.database.multithread;
import java.util.*;

enum Operation {
    ADD,
    REMOVE
}

class AddAndRemoveThread extends Thread {

    private final ArrayList<Integer> list;
    private final Operation operation;

    public AddAndRemoveThread(ArrayList<Integer> list, Operation operation) {
        this.list = list;
        this.operation = operation;
    }

    public void add(Operation operation) {
        for (int i = 0; i < 10000; i++) {
            listAction(operation);
        }
    }

    public void remove(Operation operation) {
        for (int i = 0; i < 10000; i++) {
            listAction(operation);
        }
    }

    public synchronized void listAction(Operation operation) {
        Random random = new Random();
        if (operation == Operation.REMOVE) {
            if (!list.isEmpty()) {
                list.remove(random.nextInt(list.size()));
            }
        }
        else {
            list.add(random.nextInt(10_000));
        }
    }

    public void run() {
        if (operation == Operation.ADD) {
            add(operation);
        } else {
            remove(operation);
        }
    }
}

public class Task5 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(10_000);
        Thread addAndRemoveThread1 = new AddAndRemoveThread(list, Operation.ADD);
        Thread addAndRemoveThread2 = new AddAndRemoveThread(list, Operation.REMOVE);

        addAndRemoveThread1.start();
        addAndRemoveThread2.start();
        try {
            addAndRemoveThread1.join();
            addAndRemoveThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Размер списка: " + list.size());
    }
}
