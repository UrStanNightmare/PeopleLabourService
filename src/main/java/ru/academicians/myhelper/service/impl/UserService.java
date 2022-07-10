package ru.academicians.myhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;
import ru.academicians.myhelper.service.DefaultUserService;

@Service
public class UserService implements DefaultUserService {

    private DefaultUserRepository defaultUserRepository;

    @Autowired
    public UserService(DefaultUserRepository defaultUserRepository) {
        this.defaultUserRepository = defaultUserRepository;
    }

    @Override
    public long createNewUser(AddPersonRequest request) {
        String lastName = request.getLastName().trim();
        String firstName = request.getFirstName().trim();
        String middleName = request.getMiddleName();

        if (middleName != null){
            middleName = middleName.trim();
        }

        return defaultUserRepository.saveUser(new User(lastName, firstName, middleName));
    }

    @Override
    public User findUserById(long id) {
        return defaultUserRepository.selectByIdWithoutDeals(id);
    }
}
