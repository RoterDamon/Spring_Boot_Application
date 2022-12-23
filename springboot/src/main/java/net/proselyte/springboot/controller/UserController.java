package net.proselyte.springboot.controller;

import net.proselyte.springboot.model.User;
import net.proselyte.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listCustomers(Model theModel) {
        List<User> users = userService.getUsers();
        theModel.addAttribute("users", users);
        return "user_form";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model theModel) {
        User user = new User();
        theModel.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users/list";
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/edit/{userId}")
    public ModelAndView editCustomerForm(@PathVariable("userId") Long userId) {
        ModelAndView mav = new ModelAndView("edit_user");
        User user = userService.getUser(userId);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/delete/{userId}")
    public String deleteCustomer(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/users/list";
    }
}