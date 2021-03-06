package ru.academicians.myhelper.repository.mapper.user;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.academicians.myhelper.repository.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserSecurityRowMapper  implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User result = new User();
        result.setId(rs.getLong("id"));
        result.setFirstName(rs.getString("first_name"));
        result.setLastName(rs.getString("last_name"));
        result.setMiddleName(rs.getString("middle_name"));
        result.setPassword(rs.getString("password"));
        result.setLogin(rs.getString("login"));
        result.setPhoneNumber(rs.getString("phone_number"));
        return result;
    }
}
