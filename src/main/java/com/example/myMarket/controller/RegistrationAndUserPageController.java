package com.example.myMarket.controller;

import com.example.myMarket.exeptions.IncorrectUser;
import com.example.myMarket.model.CreateUser;
import com.example.myMarket.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegistrationAndUserPageController {

    @Autowired
    UsersService usersService;


    @GetMapping("/registration")
    public String registrationPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return "redirect:/marketplace";
        }
        CreateUser dto = new CreateUser();
        model.addAttribute("dto", dto);
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String postRegistrationPage(@Valid @ModelAttribute("dto") CreateUser createUser, BindingResult bindingResult, Model model) {

        if (usersService.validateUsername(createUser)) {
            CreateUser dto = new CreateUser();
            model.addAttribute("dto", dto);
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "registrationPage";
        }
        if (bindingResult.hasErrors()) {
            CreateUser dto = new CreateUser();
            model.addAttribute("dto", dto);
            model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "registrationPage";
        }
        usersService.createUser(createUser);
        return "redirect:/marketplace";
    }

    @GetMapping("/userPage")
    public String userPage(@RequestParam("login") String login, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String name = null;
        if (userDetails != null) {
            name = userDetails.getUsername();
        }
        model.addAttribute("login", login);
        return "userPage";
    }

    @GetMapping("/err")
    public String errorLoginPage(Model model) {
        throw new IncorrectUser("Failed to input");
    }
}
