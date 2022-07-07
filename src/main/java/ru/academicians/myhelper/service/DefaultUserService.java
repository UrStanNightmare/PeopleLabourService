package ru.academicians.myhelper.service;

import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.model.User;

import java.util.List;

public interface DefaultUserService {
    List<User> getAllUsers();

    long createNewUser(AddPersonRequest request);

    User findUserById(long id);
}
