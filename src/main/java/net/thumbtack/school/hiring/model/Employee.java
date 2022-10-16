package net.thumbtack.school.hiring.model;

import java.util.Objects;

public class Employee extends User {

    public Employee(String email,
                    String lastname,
                    String firstName,
                    String middleName,
                    String login,
                    String password) {
        super(email, lastname, firstName, middleName, login, password);
    }
}
