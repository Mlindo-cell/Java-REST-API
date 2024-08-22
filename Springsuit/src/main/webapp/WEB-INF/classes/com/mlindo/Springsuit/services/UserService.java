package com.mlindo.Springsuit.services;


import com.mlindo.Springsuit.models.User;
import com.mlindo.Springsuit.repositories.UserRepository;
import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public void createUser(User user) {
        userRepository.createUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }
}
