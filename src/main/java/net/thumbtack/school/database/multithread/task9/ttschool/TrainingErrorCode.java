package net.thumbtack.school.database.multithread.task9.ttschool;

public enum TrainingErrorCode {

    TRAINEE_WRONG_FIRSTNAME("wrong firstname "),
    TRAINEE_WRONG_LASTNAME("wrong lastname "),
    TRAINEE_WRONG_RATING("wrong rating "),
    GROUP_WRONG_NAME("wrong name"),
    GROUP_WRONG_ROOM("wrong group"),
    TRAINEE_NOT_FOUND("wrong trainee"),
    SCHOOL_WRONG_NAME("wrong school"),
    DUPLICATE_GROUP_NAME("wrong group duplicate"),
    GROUP_NOT_FOUND("wrong groop"),
    DUPLICATE_TRAINEE("wrong duplicate"),

    EMPTY_TRAINEE_QUEUE("wrong queue");

    private String errorString;

    TrainingErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
