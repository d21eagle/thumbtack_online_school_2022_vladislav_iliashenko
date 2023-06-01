package net.thumbtack.school.multithread;
import java.util.*;

class AddThreadNotSynch extends Thread{
    private List<Integer> list;

    public AddThreadNotSynch(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10_000; i++) {
            add(random.nextInt(10_000));
        }
    }

    private void add(int value) {
        list.add(value);
    }
}

class RemoveThreadNotSynch extends Thread {
    private List<Integer> list;

    public RemoveThreadNotSynch(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10_000; i++) {
            if (!list.isEmpty()) {
                remove(random.nextInt(list.size()));
            }
        }
    }

    private void remove(int index) {
        list.remove(index);
    }
}

public class Task6 {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Thread addThreadNotSych = new Thread(new AddThreadNotSynch(list));
        Thread removeThreadNotSych = new Thread(new RemoveThreadNotSynch(list));

        addThreadNotSych.start();
        removeThreadNotSych.start();
        try {
            addThreadNotSych.join();
            removeThreadNotSych.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Размер списка: " + list.size());
    }
}
