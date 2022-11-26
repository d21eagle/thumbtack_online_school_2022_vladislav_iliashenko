package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class ServerResponse {

    private int responseCode;
    private String responseData;

    // REVU можно так, а можно lombok
    public ServerResponse(int responseCode, String responseData) {
        setResponseCode(responseCode);
        setResponseData(responseData);
    }

    public ServerResponse(ServerException exception) {
        // REVU сделайте static final поле
        setResponseCode(400);
        setResponseData(exception.getErrorCode().getErrorString());
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseData() {
        return responseData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerResponse response = (ServerResponse) o;
        return responseCode == response.responseCode && Objects.equals(responseData, response.responseData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, responseData);
    }
}
