package net.thumbtack.school.hiring.server.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class ServerResourceConfig extends ResourceConfig {
    public ServerResourceConfig() {
        packages("net.thumbtack.school.hiring.resources",
                "net.thumbtack.school.hiring.dto.mappers");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
}