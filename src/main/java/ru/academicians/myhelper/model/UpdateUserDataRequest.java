package ru.academicians.myhelper.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

import static ru.academicians.myhelper.defaults.DefaultRequirements.*;

public class UpdateUserDataRequest {

    @Size(min = 5, max = MAX_NAME_LENGTH)
    @Pattern(regexp = NAME_REGEXP_WITH_NULL)
    private final String firstName;

    @Size(min = 5, max = MAX_NAME_LENGTH)
    @Pattern(regexp = NAME_REGEXP_WITH_NULL)
    private final String lastName;

    @Size(min = 5, max = MAX_NAME_LENGTH)
    @Pattern(regexp = NAME_REGEXP_WITH_NULL)
    private final String middleName;

    @Size(min = 5, max = MAX_NAME_LENGTH)
    @Pattern(regexp = LOG_IN_REGEXP_WITH_NULL)
    private final String login;

    @Size(min = 5, max = MAX_NAME_LENGTH)
    @Pattern(regexp = PASSWORD_REGEXP_WITH_NULL)
    private final String password;


    @Size(min = 11, max = 12)
    @Pattern(regexp = PHONE_NUMBER_REGEXP_WITH_NULL)
    private final String phoneNumber;

    public UpdateUserDataRequest(String firstName, String lastName, String middleName, String login, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Map<String, Object> toHashMap() {
        Map<String, Object> result = new HashMap<>();
        if (firstName != null) {
            result.put("first_name", this.firstName);
        }
        if (lastName != null) {
            result.put("last_name", this.lastName);
        }
        if (middleName != null) {
            result.put("middle_name", this.middleName);
        }
        if (login != null) {
            result.put("login", this.login);
        }
        if (password != null) {
            result.put("password", this.password);
        }
        if (phoneNumber != null) {
            result.put("phone_number", this.phoneNumber);
        }

        return result;
    }

    @Override
    public String toString() {
        return "UpdateUserDataRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
