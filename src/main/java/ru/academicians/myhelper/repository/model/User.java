package ru.academicians.myhelper.repository.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class User {
    private Long id;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String firstName;

    private String middle_name;

    public User() {
    }


    public User(String lastName, String firstName, String middle_name) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middle_name = middle_name;
    }


    public User(Long id, String lastName, String firstName, String middle_name) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middle_name = middle_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && lastName.equals(user.lastName) && firstName.equals(user.firstName) && Objects.equals(middle_name, user.middle_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middle_name);
    }
}
