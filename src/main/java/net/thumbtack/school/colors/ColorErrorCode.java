package net.thumbtack.school.colors;

public enum ColorErrorCode {

    WRONG_COLOR_STRING("Wrong color %s"),
    NULL_COLOR("Null color");

    private String errorString;

    ColorErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
