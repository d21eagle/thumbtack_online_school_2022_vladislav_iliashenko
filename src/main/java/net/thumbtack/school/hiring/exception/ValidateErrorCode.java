package net.thumbtack.school.hiring.exception;

public enum ValidateErrorCode {

    EMPTY_FIRST_NAME("Пустое имя!"),
    EMPTY_MIDDLE_NAME("Пустое отчество!"),
    EMPTY_LAST_NAME("Пустая фамилия!"),
    EMPTY_LOGIN("Пустой логин!"),
    EMPTY_PASSWORD("Пустой пароль!"),
    SHORT_LOGIN("Логин слишком короткий!"),
    SHORT_PASSWORD("Пароль слишком короткий!");

    private String errorString;

    ValidateErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
