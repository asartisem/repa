package com.springapp.mvc.dao;

import com.springapp.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, new Object[] {user.getUserName(), user.getUserPassword()});
    }

    public void updateUser(User user){
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        jdbcTemplate.update(sql, new Object[] {user.getUserPassword(), user.getUserName()});
    }

    public List<User> getListUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    public void deleteUser(String name) {
        String sql = "DELETE FROM users WHERE username = ?";
        jdbcTemplate.update(sql, name);
    }

    private final static class BookRowMapper implements RowMapper<User> {

        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserName(resultSet.getString("username"));
            user.setUserPassword(resultSet.getString("password"));
            return user;
        }
    }
}
