package com.example.myMarket.controller;

import com.example.myMarket.model.Product;
import com.example.myMarket.model.Users;
import com.example.myMarket.repository.ProductRepository;
import com.example.myMarket.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Controller
public class AdminController {

    @Autowired
    ProductRepository repository;

    @Autowired
    UsersService UsersService;

//    @GetMapping("/administrate")
//    public String findAll(Model model) {
//        List<Product> productList = repository.findAll();
//        model.addAttribute("productList", productList);
//        return "administrate";
//    }

    @GetMapping("users")
    public String adminPage(Model model) {
        List<Users> allUsers = UsersService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "users";
    }

    @GetMapping("delete")
    public String delete(@RequestParam int id) {
        repository.deleteById(id);
        return "redirect:showcase?login=admin";
    }

    @GetMapping("uploadFile")
    public String uploadFile(@RequestParam int id, Model model) {
        Product product = repository.findById(id).get();
        model.addAttribute("product", product);
        return "uploadFile";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "uploadFile");
            return "redirect:showcase";
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path path = null;
        String image_Dir = "C:\\Users\\satan\\IdeaProjects\\myMarket\\src\\main\\resources\\static\\images\\";
        try {
            path = Path.of(image_Dir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        product = Optional.ofNullable(repository.findById().get()).map(product2 -> repository.findById(product.getId()).get()).orElseThrow(()->new RuntimeException("Error"));
        product.setImageName("images/" + path.getFileName().toString());
//        product.setId(product.getId());
        repository.save(product);
//        attributes.addAttribute("message", "uploaded" + fileName + "!");
        return "redirect:showcase";
    }

    @GetMapping("add")
    public String add() {
        return "add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute Product product) {
        repository.save(product);
        return "redirect:showcase?login=admin";
    }

    @GetMapping("update")
    public String update(@RequestParam int id, Model model) {
        Product product = repository.findById(id).get();
        model.addAttribute("product", product);
        return "update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute Product product) {
        repository.save(product);
        return "redirect:showcase?login=admin";
    }
}
