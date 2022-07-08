package ru.academicians.myhelper.model;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static ru.academicians.myhelper.defaults.DefaultRequirements.NAME_REGEXP;

public class AddPersonRequest {
    @NotBlank
    @NotNull
    @Pattern(regexp = NAME_REGEXP)
    private String lastName;

    @NotBlank
    @NotNull
    @Pattern(regexp = NAME_REGEXP)
    private String firstName;

    @Pattern(regexp = NAME_REGEXP)
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
