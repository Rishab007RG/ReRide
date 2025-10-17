package reride.reride_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.entity.User;
import reride.reride_backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501"})

public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/addUsers")
    public ResponseEntity<User> addUsers(@RequestBody User user){
        return ResponseEntity.ok(userService.addUsers(user));

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

