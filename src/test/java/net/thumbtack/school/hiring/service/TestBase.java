package net.thumbtack.school.hiring.service;

import net.thumbtack.school.hiring.server.Server;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    Server server = new Server();
    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }
}
