package ru.academicians.myhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.repository.UserRepository;
import ru.academicians.myhelper.repository.model.User;

import java.util.List;

@Service
public class UserService implements DefaultUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public String createNewUser(AddPersonRequest request) {
        String lastName = request.getLastName();
        String firstName = request.getFirstName();
        String patronymic = request.getPatronymic();

        userRepository.save(new User(lastName, firstName, patronymic));

        return lastName + " " + firstName + " " + patronymic;
    }
}
