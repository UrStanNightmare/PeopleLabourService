package ru.academicians.myhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.academicians.myhelper.repository.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User getUserById(Long id);

    User getUserByLastNameAndFirstNameAndPatronymic(String lastName, String firstName, String patronymic);
}
