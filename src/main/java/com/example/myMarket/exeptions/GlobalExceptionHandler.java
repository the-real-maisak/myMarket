package com.example.myMarket.exeptions;

//import com.example.myMarket.methods.Methods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

//    @Autowired
//    private Methods methods;


    @ExceptionHandler(IncorrectUser.class)
    public String IncorrectUserHandler(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails!=null)
            return "redirect:/marketplace";
        model.addAttribute("err","username или пароль введены неверно");
        return "err";
    }

}

