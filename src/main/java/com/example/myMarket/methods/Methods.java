package com.example.myMarket.methods;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


@Component
public class Methods {

    public void adminOrUser(UserDetails userDetails, Model model){
        String role=null;
        if (userDetails!=null) {
            role = String.valueOf(userDetails.getAuthorities());
            if (role.equals("ADMIN"))
                model.addAttribute("ADMIN",role);
        }
        model.addAttribute("role",role);
    }
}
