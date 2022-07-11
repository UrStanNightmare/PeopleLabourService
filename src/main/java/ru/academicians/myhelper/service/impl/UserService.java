package ru.academicians.myhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;
import ru.academicians.myhelper.security.CustomUser;
import ru.academicians.myhelper.service.DefaultUserService;

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
        String lastName = request.getLastName().trim();
        String firstName = request.getFirstName().trim();
        String middleName = request.getMiddleName();
        String password = request.getPassword().trim();
        String login = request.getLogin().trim();

        if (middleName != null){
            middleName = middleName.trim();
        }


        return defaultUserRepository.saveUser(new User(lastName, firstName, middleName, login, passwordEncoder.encode(password)));
    }



    @Override
    public User findUserById(long id) {
        return defaultUserRepository.selectByIdWithoutDeals(id);
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
}
