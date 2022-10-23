package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;

import java.util.Objects;

public class UserService {

    private String stringJson;

    public UserService(String stringJson) {
        setStringJson(stringJson);
    }

    public void setStringJson(String stringJson) {
        this.stringJson = stringJson;
    }

    public String getStringJson() {
        return stringJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserService that = (UserService) o;
        return Objects.equals(stringJson, that.stringJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringJson);
    }
}
