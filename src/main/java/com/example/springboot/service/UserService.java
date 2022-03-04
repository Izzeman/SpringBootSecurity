package com.example.springboot.service;

import com.example.springboot.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    public void createUser(User user);
    public List<User> readUsers();
    public User readUser(Long id);
    public void updateUser(Long id, User user);
    public void deleteUser(Long id);
    public UserDetails loadUserByUsername(String email);
    User getByEmail(String email);
}
