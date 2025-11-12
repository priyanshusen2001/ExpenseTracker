package com.first.ExpenseTracker.services.user;

import com.first.ExpenseTracker.dto.UserDTO;
import com.first.ExpenseTracker.entity.User;
import com.first.ExpenseTracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    //Create User
    public User createUser(UserDTO userDTO) {
        return saveORUpdateUser(userDTO,new User());
    }

    //save or update the user
    private User saveORUpdateUser(UserDTO userDTO, User user) {
        userDTO.setId(user.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        return userRepository.save(user);
    }
    //Find user by ID
    public User findUserByID(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
    }
    //Find all user
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    //Update the User By ID
    public User updateUser(UserDTO userDTO,@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return saveORUpdateUser(userDTO,user.get());
        }
        else{
            throw new EntityNotFoundException("User not found");
        }
    }

    //Delete User by ID
    public void deleteUser(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
        }
        else {
            throw new EntityNotFoundException("User not found for id: " + id);
        }
    }

}
