package net.thumbtack.school.hiring.resources;

import net.thumbtack.school.hiring.service.EmployeeService;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/api")
public class EmployeeResource {
    private final EmployeeService employeeService = new EmployeeService();

    @POST
    @Path("/hiring/employee")
    @Produces("application/json")
    public Response registerEmployee(String requestJson) {
        return employeeService.registerEmployee(requestJson);
    }

    @POST
    @Path("/hiring/employee/skill")
    @Produces("application/json")
    public Response addSkill(@HeaderParam("token") UUID token, String requestJson) {
        return employeeService.addSkill(token, requestJson);
    }

    @DELETE
    @Path("/hiring/employee/skill/{skillId}")
    @Produces("application/json")
    public Response deleteSkill(@HeaderParam("token") UUID token, @PathParam("skillId") int skillId) {
        return employeeService.deleteSkill(token, skillId);
    }

    @GET
    @Path("/hiring/employee/{token}")
    @Produces("application/json")
    public Response getCurrentEmployee(@HeaderParam("token") UUID token) {
        return employeeService.getCurrentEmployee(token);
    }

    @GET
    @Path("/hiring/employee/{token}/{skillId}")
    @Produces("application/json")
    public Response getSkillByIdExternal(@HeaderParam("token") UUID token, @PathParam("skillId") int skillId) {
        return employeeService.getSkillByIdExternal(token, skillId);
    }

    @GET
    @Path("/hiring/employee/{token}/skills")
    @Produces("application/json")
    public Response getAllSkills(@HeaderParam("token") UUID token) {
        return employeeService.getAllSkills(token);
    }
}
