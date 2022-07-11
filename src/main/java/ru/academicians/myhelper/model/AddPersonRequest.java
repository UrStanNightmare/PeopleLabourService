package ru.academicians.myhelper.model;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static ru.academicians.myhelper.defaults.DefaultRequirements.*;

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
    private String middleName;

    @NotNull
    @Pattern(regexp = LOG_IN_REGEXP)
    private String login;

    @NotNull
    @Pattern(regexp = PASSWORD_REGEXP)
    private String password;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
