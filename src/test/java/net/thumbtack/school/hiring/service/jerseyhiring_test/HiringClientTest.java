package net.thumbtack.school.hiring.service.jerseyhiring_test;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import org.junit.jupiter.api.Test;


public class HiringClientTest extends ClientTestBase {
    @Test
    public void testRegisterEmployee() {
        registerEmployee("ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579", ServerErrorCode.SUCCESS);
    }

    @Test
    public void testRegisterEmployer() {
        registerEmployer("HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801", ServerErrorCode.SUCCESS);
    }

    @Test
    public void testLoginAndLogoutUser() {
        registerEmployee("ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579", ServerErrorCode.SUCCESS);

        registerEmployer("HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801", ServerErrorCode.SUCCESS);

        String uuidEmployee = loginUser("rocket_ivan", "754376579", ServerErrorCode.SUCCESS).getToken().toString();
        logoutUser(uuidEmployee, ServerErrorCode.SUCCESS);

        String uuidEmployer = loginUser("hiretool_HRdep", "423657801", ServerErrorCode.SUCCESS).getToken().toString();
        logoutUser(uuidEmployer, ServerErrorCode.SUCCESS);
    }

    @Test
    public void testAddAndDeleteSkill() {
        registerEmployee("ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579", ServerErrorCode.SUCCESS);

        String uuidEmployee = loginUser("rocket_ivan", "754376579", ServerErrorCode.SUCCESS).getToken().toString();
        int idSkill = addSkill(uuidEmployee, "C++", 2, ServerErrorCode.SUCCESS).getSkillId();
        deleteSkill(uuidEmployee, idSkill, ServerErrorCode.SUCCESS);
    }

    @Test
    public void testAddAndDeleteVacancy() {
        registerEmployer("HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801", ServerErrorCode.SUCCESS);

        String uuidEmployer = loginUser("hiretool_HRdep", "423657801", ServerErrorCode.SUCCESS).getToken().toString();
        int idVacancy = addVacancy(uuidEmployer, "Middle", 80000, ServerErrorCode.SUCCESS).getVacancyId();
        deleteVacancy(uuidEmployer, idVacancy, ServerErrorCode.SUCCESS);
    }
    @Test
    public void testAddAndDeleteVacancyRequirement() {
        registerEmployer("HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801", ServerErrorCode.SUCCESS);

        String uuidEmployer = loginUser("hiretool_HRdep", "423657801", ServerErrorCode.SUCCESS).getToken().toString();
        int idVacancy = addVacancy(uuidEmployer, "Middle", 80000, ServerErrorCode.SUCCESS).getVacancyId();
        int idVacancyRequirement = addVacancyRequirement(uuidEmployer, idVacancy, "C++", 4, true, ServerErrorCode.SUCCESS).getRequirementId();
        deleteVacancyRequirement(uuidEmployer, idVacancyRequirement, ServerErrorCode.SUCCESS);
    }

    @Test
    public void testGetCurrentUser() {
        registerEmployee("ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579", ServerErrorCode.SUCCESS);

        String uuidEmployee = loginUser("rocket_ivan", "754376579", ServerErrorCode.SUCCESS).getToken().toString();
        getCurrentEmployee(uuidEmployee, ServerErrorCode.SUCCESS);

        registerEmployer("HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801", ServerErrorCode.SUCCESS);

        String uuidEmployer = loginUser("hiretool_HRdep", "423657801", ServerErrorCode.SUCCESS).getToken().toString();
        getCurrentEmployer(uuidEmployer, ServerErrorCode.SUCCESS);
    }

    @Test
    public void testGetSkillByIdAndAllSkills() {
        registerEmployee("ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579", ServerErrorCode.SUCCESS);

        String uuidEmployee = loginUser("rocket_ivan", "754376579", ServerErrorCode.SUCCESS).getToken().toString();
        int idSkill = addSkill(uuidEmployee, "C++", 2, ServerErrorCode.SUCCESS).getSkillId();
        addSkill(uuidEmployee, "C++", 3, ServerErrorCode.SUCCESS);
        getSkillByIdExternal(uuidEmployee, idSkill, ServerErrorCode.SUCCESS);
        getAllSkills(uuidEmployee, ServerErrorCode.SUCCESS);
    }

    @Test
    public void testGetVacancyByIdAndAllVacancies() {
        registerEmployer("HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801", ServerErrorCode.SUCCESS);

        String uuidEmployer = loginUser("hiretool_HRdep", "423657801", ServerErrorCode.SUCCESS).getToken().toString();
        int idVacancy = addVacancy(uuidEmployer, "Middle", 80000, ServerErrorCode.SUCCESS).getVacancyId();
        addVacancy(uuidEmployer, "Senior", 450000, ServerErrorCode.SUCCESS);
        getVacancyByIdExternal(uuidEmployer, idVacancy, ServerErrorCode.SUCCESS);
        getAllVacancies(uuidEmployer, ServerErrorCode.SUCCESS);
    }

    @Test
    public void testGetVacancyRequirementsByIdAndAllRequirements() {
        registerEmployer("HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801", ServerErrorCode.SUCCESS);

        String uuidEmployer = loginUser("hiretool_HRdep", "423657801", ServerErrorCode.SUCCESS).getToken().toString();
        int idVacancy = addVacancy(uuidEmployer, "Middle", 80000, ServerErrorCode.SUCCESS).getVacancyId();
        int idVacancyRequirement = addVacancyRequirement(uuidEmployer, idVacancy, "C++", 4, true, ServerErrorCode.SUCCESS).getRequirementId();
        addVacancyRequirement(uuidEmployer, idVacancy, "Java", 5, false, ServerErrorCode.SUCCESS);
        getRequirementByIdExternal(uuidEmployer, idVacancyRequirement, ServerErrorCode.SUCCESS);
        getAllRequirements(uuidEmployer, ServerErrorCode.SUCCESS);
    }
}

