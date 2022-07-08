package ru.academicians.myhelper.service;

import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.model.User;

public interface DefaultUserService {

    long createNewUser(AddPersonRequest request);

    User findUserById(long id);
}
