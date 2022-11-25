package net.thumbtack.school.hiring.exception;

public enum ServerErrorCode {
    EMPTY_FIRST_NAME("Empty first name!"),
    EMPTY_MIDDLE_NAME("Empty middle name!"),
    EMPTY_LAST_NAME("Empty last name!"),
    EMPTY_LOGIN("Empty login!"),
    EMPTY_PASSWORD("Empty password!"),
    WRONG_LOGIN_OR_PASSWORD("Wrong login or password!"),
    SHORT_LOGIN("Short login!"),
    SHORT_PASSWORD("Short password!"),
    EMPTY_COMPANY_NAME("Empty company name!"),
    EMPTY_COMPANY_ADDRESS("Empty company address!"),
    WRONG_JSON("JSON is wrong"),
    SESSION_NOT_FOUND("Session not found!"),
    LOGIN_ALREADY_USED("Login already exist!"),
    INVALID_TOKEN("Token is wrong!"),
    INVALID_USERTYPE("Usertype is wrong!");

    private String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
