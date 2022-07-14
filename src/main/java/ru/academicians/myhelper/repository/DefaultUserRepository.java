package ru.academicians.myhelper.repository;

import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.repository.model.User;

import java.util.Map;

public interface DefaultUserRepository {
    long saveUser(User user);

    User findUserByLogin(String login);

    int deleteUserAndSubscriptionDataById(Long id);

    DetailedUserInfoResponse getDetailedUserInfoById(long id);

    User findUserById(long id);

    String updateUserData(long id, Map<String, Object> updateData);
}
