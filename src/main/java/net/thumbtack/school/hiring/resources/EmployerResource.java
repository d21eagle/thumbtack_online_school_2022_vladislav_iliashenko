package net.thumbtack.school.hiring.resources;

import net.thumbtack.school.hiring.service.EmployerService;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/api")
public class EmployerResource {
    private final EmployerService employerService = new EmployerService();

    @POST
    @Path("/hiring/employer")
    @Produces("application/json")
    public Response registerEmployee(String requestJson) {
        return employerService.registerEmployer(requestJson);
    }

    @POST
    @Path("/hiring/employer/vacancy")
    @Produces("application/json")
    public Response addVacancy(@HeaderParam("token") UUID token, String requestJson) {
        return employerService.addVacancy(token, requestJson);
    }

    @POST
    @Path("/hiring/employer/vacancy/vacancy_requirement")
    @Produces("application/json")
    public Response addVacancyRequirement(@HeaderParam("token") UUID token, String requestJson) {
        return employerService.addVacancyRequirement(token, requestJson);
    }

    @DELETE
    @Path("/hiring/employer/vacancy/{vacancyId}")
    @Produces("application/json")
    public Response deleteVacancy(@HeaderParam("token") UUID token, @PathParam("vacancyId") int vacancyId) {
        return employerService.deleteVacancy(token, vacancyId);
    }

    @DELETE
    @Path("/hiring/employer/vacancy/vacancy_requirement/{requirementId}")
    @Produces("application/json")
    public Response deleteVacancyRequirement(@HeaderParam("token") UUID token, @PathParam("requirementId") int requirementId) {
        return employerService.deleteVacancyRequirement(token, requirementId);
    }

    @GET
    @Path("/hiring/employer/{token}")
    @Produces("application/json")
    public Response getCurrentEmployer(@HeaderParam("token") UUID token) {
        return employerService.getCurrentEmployer(token);
    }

    @GET
    @Path("/hiring/employer/{token}/{vacancyId}")
    @Produces("application/json")
    public Response getVacancyByIdExternal(@HeaderParam("token") UUID token, @PathParam("vacancyId") int vacancyId) {
        return employerService.getVacancyByIdExternal(token, vacancyId);
    }

    @GET
    @Path("/hiring/employer/{token}/vacancy/{requirementId}")
    @Produces("application/json")
    public Response getRequirementByIdExternal(@HeaderParam("token") UUID token, @PathParam("requirementId") int requirementId) {
        return employerService.getRequirementByIdExternal(token, requirementId);
    }

    @GET
    @Path("/hiring/employer/{token}/vacancies")
    @Produces("application/json")
    public Response getAllVacancies(@HeaderParam("token") UUID token) {
        return employerService.getAllVacancies(token);
    }

    @GET
    @Path("/hiring/employer/{token}/vacancies/vacancy_requirement")
    @Produces("application/json")
    public Response getAllRequirements(@HeaderParam("token") UUID token) {
        return employerService.getAllRequirements(token);
    }
}
