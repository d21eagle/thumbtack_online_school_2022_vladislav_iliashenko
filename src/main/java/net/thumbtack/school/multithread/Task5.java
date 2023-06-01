package net.thumbtack.school.multithread;
import java.util.*;

enum Operation {
    ADD,
    REMOVE
}

class ListWrapper<T> {
    private List<Integer> list;

    public ListWrapper() {
        list = new ArrayList<>();
    }

    public synchronized void addElement(int element) {
        list.add(element);
    }

    public synchronized void removeElement(int index) {
        if (!list.isEmpty()) {
            list.remove(index);
        }
    }

    public synchronized int getSize() {
        return list.size();
    }
}

class AddAndRemoveThread implements Runnable {
    private static final int OPERATIONS_COUNT = 10000;
    private ListWrapper<Integer> listWrapper;
    private Operation operation;

    public AddAndRemoveThread(ListWrapper<Integer> listWrapper, Operation operation) {
        this.listWrapper = listWrapper;
        this.operation = operation;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            if (operation == Operation.ADD) {
                listWrapper.addElement(random.nextInt(100));
            }
            else if (operation == Operation.REMOVE) {
                int size = listWrapper.getSize();
                if (size > 0) {
                    listWrapper.removeElement(random.nextInt(size));
                }
            }
        }
    }
}


public class Task5 {
    public static void main(String[] args) {
        ListWrapper<Integer> listWrapper = new ListWrapper<>();
        Thread addThread = new Thread(new AddAndRemoveThread(listWrapper, Operation.ADD));
        Thread removeThread = new Thread(new AddAndRemoveThread(listWrapper, Operation.REMOVE));

        addThread.start();
        removeThread.start();
        try {
            addThread.join();
            removeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Размер списка: " + listWrapper.getSize());
    }
}
