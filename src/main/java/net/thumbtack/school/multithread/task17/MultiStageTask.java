package net.thumbtack.school.multithread.task17;

import net.thumbtack.school.multithread.task16.Executable;
import java.util.*;

public class MultiStageTask implements Executable {
    String name;
    private List<Executable> stages;
    private int currentStage;

    public MultiStageTask(String name, List<Executable> stages) {
        this.name = name;
        this.stages = stages;
        this.currentStage = 0;
    }

    public List<Executable> getStages() {
        return stages;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    @Override
    public void execute() {
        if (currentStage < stages.size()) {
            stages.get(currentStage).execute();
            currentStage++;
        }
    }
}

