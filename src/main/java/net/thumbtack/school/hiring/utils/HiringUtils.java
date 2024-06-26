package net.thumbtack.school.hiring.utils;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.response.FailureResponse;
import net.thumbtack.school.hiring.exception.ServerException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HiringUtils {
    private static final Gson GSON = new Gson();

    public static Response failureResponse(Response.Status status, ServerException ex) {
        return Response.status(status)
                .entity(GSON.toJson(new FailureResponse(ex.getUserErrorCode(), ex.getMessage())))
                .type(MediaType.APPLICATION_JSON).build();
    }

    public static Response failureResponse(ServerException ex) {
        return failureResponse(Response.Status.BAD_REQUEST, ex);
    }
}
