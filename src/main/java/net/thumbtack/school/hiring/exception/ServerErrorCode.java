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
    INVALID_TOKEN("Invalid token!"),
    INVALID_USERTYPE("Usertype is wrong!"),
    EMPTY_SKILL_NAME("Empty skill name!"),
    INVALID_PROF_LEVEL("Invalid prof level!"),
    INVALID_ID("Id is invalid!"),
    EMPTY_POSITION("Position is empty!"),
    INVALID_SALARY("Invalid salary!"),
    EMPTY_REQUIREMENT_NAME("Empty requirement name!"),
    USER_NOT_EXIST("User not exist!");

    private String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
