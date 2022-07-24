package com.example.myMarket.controller;

import com.example.myMarket.model.Product;
import com.example.myMarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Controller
//@RequestMapping("/marketplace")
public class ProductController {
    @Autowired
    ProductRepository repository;

    @GetMapping("/marketplace")
    public String main() {
        return "marketplace";
    }

    @GetMapping("/showcase")
    public String findAll(Model model) {
        List<Product> productList = repository.findAll();
        model.addAttribute("productList", productList);
        return "showcase";
    }

    @GetMapping("/product")
    public String product(@RequestParam int id, Model model) {
        Product product = repository.findById(id).get();
        model.addAttribute("product", product);
        return "product";
    }


}
