package net.thumbtack.school.hiring.exception;

public enum ServerErrorCode {

    EMPTY_FIRST_NAME("Пустое имя!"),
    EMPTY_MIDDLE_NAME("Пустое отчество!"),
    EMPTY_LAST_NAME("Пустая фамилия!"),
    EMPTY_LOGIN("Пустой логин!"),
    EMPTY_PASSWORD("Пустой пароль!"),
    WRONG_LOGIN_OR_PASSWORD("Неправильный логин или пароль!"),
    SHORT_LOGIN("Логин слишком короткий!"),
    SHORT_PASSWORD("Пароль слишком короткий!"),
    EMPTY_COMPANY_NAME("Пустое название компании!"),
    EMPTY_COMPANY_ADDRESS("Пустой адрес компании!"),
    WRONG_JSON("JSON is wrong"),
    SESSION_NOT_FOUND("Сессия не найдена!"),
    EXIST_LOGIN("Этот логин уже существует!");

    private String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
