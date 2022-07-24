package com.example.myMarket.service;

import com.example.myMarket.model.CreateUser;
import com.example.myMarket.model.Role;
import com.example.myMarket.model.Users;
import com.example.myMarket.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UserDetailsService, UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users getUser(int id) {
        Users user=null;
        Optional<Users> usersOptional=usersRepository.findById(id);
        if(usersOptional.isPresent()){
            user=usersOptional.get();
        }
        return user;
    }

    @Override
    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username).map(users -> new User(users.getUsername()
                        ,users.getPassword()
                        , Collections.singleton(users.getRole())))
                .orElseThrow(()->new UsernameNotFoundException("Failed to retrieve user: "+username));
    }

    @Override
    public void createUser(CreateUser usersDto) {
        Users users=new Users();
        users.setUsername(usersDto.getUsername());
        users.setRole(Role.USER);
        Optional.ofNullable(usersDto.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(users::setPassword);
        usersRepository.save(users);
    }

    @Override
    public boolean validateUsername(CreateUser usersDto) {
        return usersRepository.findByUsername(usersDto.getUsername()).isPresent();
    }
}
