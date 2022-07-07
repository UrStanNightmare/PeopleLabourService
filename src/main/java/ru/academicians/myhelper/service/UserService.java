package ru.academicians.myhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;

import java.util.List;

@Service
public class UserService implements DefaultUserService {

    private DefaultUserRepository defaultUserRepository;

    @Autowired
    public UserService(DefaultUserRepository defaultUserRepository) {
        this.defaultUserRepository = defaultUserRepository;
    }

    private String quackBuilder(Integer countRepetition, Integer countQuacks) {
        StringBuilder builder = new StringBuilder(50);
        for (int i = 0; i < countQuacks; i++) {
            builder.append("qua");
        }

        return builder.toString();
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public long createNewUser(AddPersonRequest request) {
        String lastName = request.getLastName().trim();
        String firstName = request.getFirstName().trim();
        String patronymic = request.getPatronymic();

        if (patronymic != null){
            patronymic = patronymic.trim();
        }

        User save = defaultUserRepository.save(new User(lastName, firstName, patronymic));

        return save.getId();
    }

    @Override
    public User findUserById(long id) {
        return defaultUserRepository.getUserById(id);
    }
}
