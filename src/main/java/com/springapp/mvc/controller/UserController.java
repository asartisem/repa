package com.springapp.mvc.controller;

import com.springapp.mvc.model.User;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private UserService userService;

    @Autowired()
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String Users (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userService.getListUsers());
        return "users";
    }


    @RequestMapping("/remove-user/{username}")
    public String removeUser(@PathVariable("username") String name){
        this.userService.deleteUser(name);

        return "redirect:/users";
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST, produces = {"text/html; charset=utf-8"})
    public String addUsers(@ModelAttribute("user") User user) {

        this.userService.addUser(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST, produces = {"text/html; charset=utf-8"})
    public String updateUsers(@ModelAttribute("user") User user) {

        this.userService.updateUser(user);

        return "redirect:/users";
    }


    @ExceptionHandler(value = DuplicateKeyException.class)
    public ModelAndView dublicateExceptionHandlerUsers() {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", "Пользователь c таким именем уже есть");
        return modelAndView;
    }


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ModelAndView removeExceptionHandlerForUsers() {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", "Нельзя удалить пользователя с книгой на руках");
        return modelAndView;
    }
}
