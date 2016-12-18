package com.springapp.mvc.service;

import com.springapp.mvc.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(String name);

    List<User> getListUsers();
}
