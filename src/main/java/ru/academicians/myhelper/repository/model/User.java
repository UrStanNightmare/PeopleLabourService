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

    @NotNull
    @NotBlank
    private String patronymic;

    public User() {
    }


    public User(String lastName, String firstName, String patronymic) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }


    public User(Long id, String lastName, String firstName, String patronymic) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && lastName.equals(user.lastName) && firstName.equals(user.firstName) && Objects.equals(patronymic, user.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, patronymic);
    }
}
