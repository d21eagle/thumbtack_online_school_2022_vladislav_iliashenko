package net.thumbtack.school.hiring.dto.mappers;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.utils.HiringUtils;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class MethodNotAllowedExceptionMapper implements	ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException exception) {
        return HiringUtils.failureResponse(Status.METHOD_NOT_ALLOWED,
                new ServerException(ServerErrorCode.METHOD_NOT_ALLOWED));
    }
}