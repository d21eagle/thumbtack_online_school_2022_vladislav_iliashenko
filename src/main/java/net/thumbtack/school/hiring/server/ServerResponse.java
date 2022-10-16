package net.thumbtack.school.hiring.server;

public class ServerResponse {

    private int responseCode;
    private String responseData;

    public ServerResponse(int responseCode, String responseData) {
        setResponseCode(responseCode);
        setResponseData(responseData);
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
}
