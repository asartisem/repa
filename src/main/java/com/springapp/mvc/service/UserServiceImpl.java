package com.springapp.mvc.service;

import com.springapp.mvc.dao.UserDAO;
import com.springapp.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) {
        this.userDAO.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        this.userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(String name) {
        this.userDAO.deleteUser(name);
    }

    @Override
    public List<User> getListUsers() {
        return this.userDAO.getListUsers();
    }
}
