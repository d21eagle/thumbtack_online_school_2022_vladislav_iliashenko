package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.server.Server;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    Server server = new Server();
    protected static final Gson GSON = new Gson();
    protected static final int SUCCESS_CODE = 200;
    protected static final int ERROR_CODE = 400;
    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }
}
