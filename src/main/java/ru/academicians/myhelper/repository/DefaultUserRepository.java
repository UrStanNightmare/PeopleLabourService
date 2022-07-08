package ru.academicians.myhelper.repository;

import ru.academicians.myhelper.repository.model.User;

public interface DefaultUserRepository {
    User selectByIdWithoutDeals(long id);

    long saveUser(User user);
}
