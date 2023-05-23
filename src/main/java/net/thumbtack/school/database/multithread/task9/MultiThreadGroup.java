package net.thumbtack.school.database.multithread.task9;
import net.thumbtack.school.database.multithread.task9.ttschool.*;
import java.util.*;
import lombok.*;

@AllArgsConstructor
class GroupThread extends Thread {
    private final Group group;
    private final Operation operation;

    public void addTrainees() {
        for (int i = 0; i < 50; i++) {
            try {
                String name = String.format("Учение: %s", i);
                group.addTrainee(new Trainee(name, "Фамилия ученика", 3));
                System.out.printf("Ученик%s добавлен\n", i);
                Thread.sleep(200);
            } catch (InterruptedException | TrainingException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeTrainees() {
        for (int i = 0; i < 50; i++) {
            try {
                group.removeTrainee(i);
                System.out.printf("Ученик%s удалён\n", i);
                Thread.sleep(200);
            } catch (InterruptedException | TrainingException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        if (operation == Operation.ADD) {
            addTrainees();
        } else {
            removeTrainees();
        }
    }
}

public class MultiThreadGroup {
    public static void main(String[] args) throws TrainingException{
        Group group = new Group("Имя ученика", "Кабинет");
        fillGroup(group);

        GroupThread addTrainees = new GroupThread(group, Operation.ADD);
        GroupThread removeTrainees = new GroupThread(group, Operation.REMOVE);
        addTrainees.start();
        removeTrainees.start();

        try {
            addTrainees.join();
            removeTrainees.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void fillGroup(Group group) {
        List<Trainee> trainees = group.getTrainees();
        for (int i = 0; i < 50; i++) {
            try {
                String name = String.format("Ученик: %s", i);
                trainees.add(new Trainee(name, "Фамилия ученика", 3));
            } catch (TrainingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
