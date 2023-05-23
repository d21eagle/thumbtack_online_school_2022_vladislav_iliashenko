package net.thumbtack.school.database.multithread.task9;
import net.thumbtack.school.database.multithread.task9.ttschool.*;
import java.util.*;
import lombok.*;

@AllArgsConstructor
class SchoolThread extends Thread {
    private final School school;
    private final Operation operation;

    public void addGroups() {
        for (int i = 50; i < 100; i++) {
            synchronized (school) {
                try {
                    String name = String.format("Ученик%s", i);
                    school.addGroup(new Group(name, "Кабинет"));
                    System.out.printf("Группа%s добавлена \n", i);
                    Thread.sleep(200);
                } catch (TrainingException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeGroups() {
        for (int i = 0; i < 50; i++) {
            synchronized (school) {
                try {
                    school.removeGroup(String.format("Группа%s", i));
                    System.out.printf("Группа%s удалена \n", i);
                    Thread.sleep(100);
                } catch (TrainingException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        if (operation == Operation.ADD) {
            addGroups();
        } else {
            removeGroups();
        }
    }
}

public class MultiThreadSchool {
    public static void main(String[] args) throws TrainingException {
        School school = new School("Школа", 2023);
        fillSchool(school);

        SchoolThread addGroups = new SchoolThread(school, Operation.ADD);
        SchoolThread removeGroups = new SchoolThread(school, Operation.REMOVE);
        addGroups.start();
        removeGroups.start();

        try {
            addGroups.join();
            removeGroups.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void fillSchool(School school){
        Set<Group> set = school.getGroups();
        for (int i = 0; i < 50; i++) {
            try {
                String name = String.format("Группа%s", i);
                set.add(new Group(name, "Кабинет"));
            } catch (TrainingException e) {
                e.printStackTrace();
            }
        }
    }
}
