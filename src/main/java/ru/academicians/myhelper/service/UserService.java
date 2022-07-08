package ru.academicians.myhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;

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
        String patronymic = request.getPatronymic();

        if (patronymic != null){
            patronymic = patronymic.trim();
        }

        return defaultUserRepository.saveUser(new User(lastName, firstName, patronymic));
    }

    @Override
    public User findUserById(long id) {
        return defaultUserRepository.selectByIdWithoutDeals(id);
    }
}
