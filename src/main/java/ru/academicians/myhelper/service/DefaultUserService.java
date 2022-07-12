package ru.academicians.myhelper.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.model.UpdateUserDataRequest;

public interface DefaultUserService extends UserDetailsService {

    long createNewUser(AddPersonRequest request);

    int deleteUserAndSubscriptionData(long id);

    DetailedUserInfoResponse getDetailedUserInfoById(long id);

    String updateUserWithData(long id, UpdateUserDataRequest request);
}
