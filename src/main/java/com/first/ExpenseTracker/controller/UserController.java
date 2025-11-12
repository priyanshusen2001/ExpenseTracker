package com.first.ExpenseTracker.controller;

import com.first.ExpenseTracker.dto.UserDTO;
import com.first.ExpenseTracker.entity.User;
import com.first.ExpenseTracker.services.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    final UserService userService;
    //Create User
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO){
        User createdUser = userService.createUser(userDTO);
        if(createdUser!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }
    //Find user by ID
    @GetMapping("{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByID(id));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    //Find all user
    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
    }
    //Update the User By ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.updateUser(userDTO,id));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    //Delete User by ID
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(null);
        }
        catch (EntityNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong..");
        }
    }
}
