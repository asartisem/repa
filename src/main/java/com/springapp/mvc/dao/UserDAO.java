package com.springapp.mvc.dao;

import com.springapp.mvc.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(String name);

    List<User> getListUsers();
}
