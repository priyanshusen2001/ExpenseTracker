package com.first.ExpenseTracker.services.user;

import com.first.ExpenseTracker.dto.UserDTO;
import com.first.ExpenseTracker.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);
    public List<User> findAllUsers();
    public User findUserByID(@PathVariable Long id);
    void deleteUser(@PathVariable Long id);
    User updateUser(UserDTO userDTO,@PathVariable Long id);
}
