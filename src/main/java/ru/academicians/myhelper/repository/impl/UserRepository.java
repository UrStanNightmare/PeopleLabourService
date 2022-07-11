package ru.academicians.myhelper.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class UserRepository implements DefaultUserRepository {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;
    private RowMapper<User> userSecurityRowMapper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate,
                          @Qualifier("userEntityRowMapper") RowMapper<User> userRowMapper,
                          @Qualifier("userSecurityRowMapper") RowMapper<User> userSecurityRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.userSecurityRowMapper = userSecurityRowMapper;
    }

    @Override
    public User selectByIdWithoutDeals(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE id = ?;",
                    new Object[]{id},
                    userRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public long saveUser(User user) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users(first_name, last_name, middle_name, login, password) VALUES (? ,? ,?, ?, ? )",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getMiddleName());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getPassword());
            return ps;
        }, keyHolder);


        long id = (Long) keyHolder.getKeys().get("id");

        return id;
    }

    @Override
    public User selectByFullNameData(String firstName, String middleName, String lastName) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE last_name LIKE ? AND first_name LIKE ? AND middle_name LIKE ?;",
                    new Object[]{lastName, firstName, middleName},
                    userRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public User findUserByLogin(String login) {

        Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        String userSQLQuery = "SELECT * FROM users WHERE login=?";
        List<User> list = jdbcTemplate.query(userSQLQuery, new String[]{login},
                (ResultSet rs, int rowNum) -> {
                    User user = new User();
                    user.setLogin(login);
                    user.setPassword(rs.getString("password"));
                    user.setId(rs.getLong("id"));
                    return user;
                });

        if (!list.isEmpty()) {
            User userToReturn = list.get(0);
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            GrantedAuthority grantedIdAuthority = new SimpleGrantedAuthority("id:" +userToReturn.getId().toString());
            grantedAuthoritiesList.add(grantedAuthority);
            grantedAuthoritiesList.add(grantedIdAuthority);
            userToReturn.setGrantedAuthoritiesList(grantedAuthoritiesList);
            return userToReturn;
        }
        return null;
    }
}
