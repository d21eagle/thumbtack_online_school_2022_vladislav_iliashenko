package net.thumbtack.school.hiring.model;

import java.util.Objects;

// REVU equals, hashCode
// без них с HashSet Вас ждут неприятные сюрпризы
// здесь и в других классах
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
}
