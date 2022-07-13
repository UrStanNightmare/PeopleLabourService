package ru.academicians.myhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.model.UpdateUserDataRequest;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;
import ru.academicians.myhelper.security.CustomUser;
import ru.academicians.myhelper.service.DefaultUserService;

import java.util.Map;

import static ru.academicians.myhelper.defaults.DefaultKeys.USERNAME_OCCUPIED;
import static ru.academicians.myhelper.defaults.DefaultKeys.USER_NOT_FOUND_STRING;

@Service
public class UserService implements DefaultUserService {

    private DefaultUserRepository defaultUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(DefaultUserRepository defaultUserRepository, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.defaultUserRepository = defaultUserRepository;
        this.passwordEncoder = passwordEncoder1;
    }

    @Override
    public long createNewUser(AddPersonRequest request) {
        User userByLogin = defaultUserRepository.findUserByLogin(request.getLogin());

        if (userByLogin != null) {
            throw new IllegalArgumentException(USERNAME_OCCUPIED);
        }

        String lastName = request.getLastName().trim();
        String firstName = request.getFirstName().trim();
        String middleName = request.getMiddleName();
        String password = request.getPassword().trim();
        String login = request.getLogin().trim();

        if (middleName != null) {
            middleName = middleName.trim();
        }

        return defaultUserRepository.saveUser(new User(lastName, firstName, middleName, login, passwordEncoder.encode(password)));
    }

    @Override
    public int deleteUserAndSubscriptionData(long id) {
        int deleteCount = 0;

        DetailedUserInfoResponse detailedUserInfoById = defaultUserRepository.getDetailedUserInfoById(id);

        deleteCount += defaultUserRepository.deleteUserAndSubscriptionDataById(detailedUserInfoById.getId());

        return deleteCount;
    }

    @Override
    public DetailedUserInfoResponse getDetailedUserInfoById(long id) {

        DetailedUserInfoResponse detailedUserInfoById = defaultUserRepository.getDetailedUserInfoById(id);

        if (detailedUserInfoById == null) {
            throw new ItemNotFoundException(USER_NOT_FOUND_STRING);
        }

        return detailedUserInfoById;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        try {
            user = defaultUserRepository.findUserByLogin(login);
            CustomUser customUser = new CustomUser(user);

            return customUser;
        } catch (Exception e) {
            throw new UsernameNotFoundException("User " + login + " not found!");
        }
    }

    @Override
    public String updateUserWithData(long id, UpdateUserDataRequest request) {
        User user = defaultUserRepository.findUserById(id);

        if (user == null) {
            throw new ItemNotFoundException(USER_NOT_FOUND_STRING);
        }

        Map<String, Object> updateArgs = request.toHashMap();
        if (updateArgs.isEmpty()) {
            return "nothing";
        }

        if (updateArgs.containsKey("login")) {
            User login = defaultUserRepository.findUserByLogin((String) updateArgs.get("login"));
            if (id != login.getId()) {
                throw new IllegalArgumentException(USERNAME_OCCUPIED);
            }
        }

        if (updateArgs.containsKey("password")) {
            updateArgs.put("password", passwordEncoder.encode((String) updateArgs.get("password")));
        }

        return defaultUserRepository.updateUserData(id, updateArgs);
    }
}
