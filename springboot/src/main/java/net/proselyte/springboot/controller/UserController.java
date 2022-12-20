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

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listCustomers(Model theModel) {
        List<User> users = userService.getUsers();
        theModel.addAttribute("users", users);
        return "user_form";
    }

    @RequestMapping("/new")
    public String showFormForAdd(Model theModel) {
        User user = new User();
        theModel.addAttribute("user", user);
        return "new_user";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        if (Objects.isNull(user.getId())) {
            userService.saveUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:/users";
    }

    @RequestMapping("/edit")
    public ModelAndView editCustomerForm(@RequestParam(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_user");
        User user = userService.getUser(id);
        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
