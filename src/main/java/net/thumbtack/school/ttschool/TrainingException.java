package net.thumbtack.school.ttschool;

public class TrainingException extends Exception {

    private TrainingErrorCode errorCode;

    public TrainingException(TrainingErrorCode errorCode) {
        super(errorCode.getErrorString());
        this.errorCode = errorCode;
    }

    public TrainingErrorCode getErrorCode() {
        return errorCode;
    }
}
