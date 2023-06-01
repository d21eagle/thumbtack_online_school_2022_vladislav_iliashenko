package net.thumbtack.school.multithread.task16;
import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
public class Task implements Executable {
    private String name;

    public void execute() {
        System.out.printf("Поток %s делает %s\n", Thread.currentThread().getId(), getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.printf("%s сделана потоком %s\n", getName(), Thread.currentThread().getId());
    }
}
