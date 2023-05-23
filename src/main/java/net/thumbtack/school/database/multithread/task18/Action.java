package net.thumbtack.school.database.multithread.task18;

enum ActionCondition {
    TASK_CREATED,
    TASK_FINISHED,
    WRITER_START_WORK,
    WRITER_FINISH_WORK
}

public class Action {
    private final ActionCondition type;

    public Action(ActionCondition type) {
        this.type = type;
    }

    public ActionCondition getType() {
        return type;
    }
}
