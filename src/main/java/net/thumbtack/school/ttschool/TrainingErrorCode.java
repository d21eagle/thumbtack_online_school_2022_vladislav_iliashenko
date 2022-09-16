package net.thumbtack.school.ttschool;

public enum TrainingErrorCode {

    TRAINEE_WRONG_FIRSTNAME("wrong firstname "),
    TRAINEE_WRONG_LASTNAME("wrong lastname "),
    TRAINEE_WRONG_RATING("wrong rating ");

    private String errorString;

    TrainingErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
