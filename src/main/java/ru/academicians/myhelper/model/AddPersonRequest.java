package ru.academicians.myhelper.model;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.academicians.myhelper.defaults.DefaultRequirements.MAX_NAME_LENGTH;

public class AddPersonRequest {
    @NotBlank
    @NotNull
    @Size(max = MAX_NAME_LENGTH)
    private String lastName;

    @NotBlank
    @NotNull
    @Size(max = MAX_NAME_LENGTH)
    private String firstName;

    @Size(max = MAX_NAME_LENGTH)
    @Nullable
    private String patronymic;

    public AddPersonRequest() {
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
}
