package net.thumbtack.school.hiring.dto.request;

import java.util.Objects;

public class RegisterEmployerDtoRequest extends RegisterUserDtoRequest {

    private String companyName;
    private String companyAddress;

    public RegisterEmployerDtoRequest(String companyName,
                    String companyAddress,
                    String email,
                    String lastname,
                    String firstName,
                    String middleName,
                    String login,
                    String password) {
        super(email, lastname, firstName, middleName, login, password);
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterEmployerDtoRequest that = (RegisterEmployerDtoRequest) o;
        return Objects.equals(companyName, that.companyName) && Objects.equals(companyAddress, that.companyAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, companyAddress);
    }
}
