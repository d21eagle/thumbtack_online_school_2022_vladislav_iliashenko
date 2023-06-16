package net.thumbtack.school.hiring.service.sql;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import net.thumbtack.school.hiring.utils.Settings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    Server server = new Server();
    protected static final Gson GSON = new Gson();
    protected static final int SUCCESS_CODE = 200;
    protected static final int ERROR_CODE = 400;
    private static final Settings settings = Settings.getInstance();

    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }

    @BeforeAll
    public static void setUp() {
        MyBatisUtils.initSqlSessionFactory();
        settings.setDatabaseType("SQL");
    }
}
