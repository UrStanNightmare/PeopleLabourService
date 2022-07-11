package ru.academicians.myhelper.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.model.User;

public interface DefaultUserService extends UserDetailsService {

    long createNewUser(AddPersonRequest request);

    User findUserById(long id);
}
