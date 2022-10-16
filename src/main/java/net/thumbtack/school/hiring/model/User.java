package net.thumbtack.school.hiring.model;

import java.util.Objects;

public class User {

    private String email;
    private String login;
    private String password;
    private String lastName;
    private String middleName;
    private String firstName;

    public User(String email,
                String lastname,
                String firstName,
                String middleName,
                String login,
                String password) {
        setEmail(email);
        setLastName(lastname);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLogin(login);
        setPassword(password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(lastName, user.lastName) && Objects.equals(middleName, user.middleName) && Objects.equals(firstName, user.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, login, password, lastName, middleName, firstName);
    }
}
