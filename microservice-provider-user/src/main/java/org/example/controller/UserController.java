package org.example.controller;

import org.example.dao.UserRepository;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        return  this.userRepository.findOne(id);
    }
    @GetMapping("api/user/{id}")
    public User findByIdApi(@PathVariable Long id){
        return  this.userRepository.findOne(id);
    }
    @GetMapping("user/{id}")
    public User findByIduser(@PathVariable Long id){
        return  this.userRepository.findOne(id);
    }
    @GetMapping("getByName1")
    public User getByName1(@RequestParam("id") Long id , @RequestParam("username") String username){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
    @GetMapping("getByName2")
    public User getByName2(@RequestParam Map<String,Object> map){
        User user = new User();
        user.setId(Long.valueOf((String) map.get("id")));
        user.setUsername((String) map.get("username"));
        user.setAge(80);
        return user;
    }
    @PostMapping("getByName3")
    public User getByName3(@RequestBody User user){
        return user;
    }
}
