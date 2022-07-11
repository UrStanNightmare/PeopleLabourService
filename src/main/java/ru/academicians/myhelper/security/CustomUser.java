package ru.academicians.myhelper.security;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
    public CustomUser(ru.academicians.myhelper.repository.model.User user) {
        super(user.getLogin(), user.getPassword(), user.getGrantedAuthoritiesList());
    }
}
