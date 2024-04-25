package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/")
    public @ResponseBody Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> read(@PathVariable long id) {
        User user;
        if (userRepository.findById(id).isPresent()) {
            user = (User) userRepository.findById(id).get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> delete(@PathVariable long id) {
        User user;
        if (userRepository.findById(id).isPresent()) {
            user = (User) userRepository.findById(id).get();
            userRepository.deleteById(id);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/")
    public ResponseEntity<User> create(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody User user) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<User> patch(@PathVariable long id, @RequestBody User user) {
        if (userRepository.findById(id).isPresent()) {
            User existingUser = (User) userRepository.findById(id).get();
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
