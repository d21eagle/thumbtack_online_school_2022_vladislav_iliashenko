package net.thumbtack.school.hiring.model;

import java.util.Objects;

public class Employer extends User {

    private String companyName;
    private String companyAddress;

    public Employer(String companyName,
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
        if (!super.equals(o)) return false;
        Employer employer = (Employer) o;
        return Objects.equals(companyName, employer.companyName) && Objects.equals(companyAddress, employer.companyAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, companyAddress);
    }
}
