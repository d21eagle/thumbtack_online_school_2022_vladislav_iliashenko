package net.thumbtack.school.database.multithread.task17;
import net.thumbtack.school.database.multithread.task16.Executable;
import java.util.*;
import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
public class MultiStageTask implements Executable {
    private String name;
    private List<Executable> list;

    public void execute() {
        System.out.printf("Поток %s делает %s\n", Thread.currentThread().getId(), getName());
    }
}

