package ru.academicians.myhelper.model;

import ru.academicians.myhelper.repository.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

public class DetailedUserInfoResponse {
    private Long id;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String login;

    private String middleName;

    private List<ShortDealInfoResponse> placedDeals;

    public DetailedUserInfoResponse() {
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

    public List<ShortDealInfoResponse> getPlacedDeals() {
        return placedDeals;
    }

    public void setPlacedDeals(List<ShortDealInfoResponse> placedDeals) {
        this.placedDeals = placedDeals;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void addPlacedDeals(ShortDealInfoResponse deal){
        if (this.placedDeals == null){
            this.placedDeals = new LinkedList<>();
        }
        this.placedDeals.add(deal);
    }

    public void fillUserData(User user) {
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.login = user.getLogin();
    }
}
