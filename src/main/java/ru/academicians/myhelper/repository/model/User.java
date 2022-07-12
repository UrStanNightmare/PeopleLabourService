package ru.academicians.myhelper.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@JsonIgnoreProperties(value = {
        "password"
})
public class User {
    private Long id;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String firstName;

    private String middleName;

    @NotNull
    @NotBlank
    private String login;

    @NotNull
    @NotBlank
    private String password;

    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    public User() {
    }

    public User(Long id, String lastName, String firstName, String middleName, String login, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
    }

    public User(String lastName, String firstName, String middleName, String login, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
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

    public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
        return grantedAuthoritiesList;
    }

    public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
        this.grantedAuthoritiesList = grantedAuthoritiesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && lastName.equals(user.lastName) && firstName.equals(user.firstName) && Objects.equals(middleName, user.middleName) && login.equals(user.login) && Objects.equals(password, user.password) && Objects.equals(grantedAuthoritiesList, user.grantedAuthoritiesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middleName, login, password, grantedAuthoritiesList);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", login='" + login + '\'' +
                ", grantedAuthoritiesList=" + grantedAuthoritiesList +
                '}';
    }
}
