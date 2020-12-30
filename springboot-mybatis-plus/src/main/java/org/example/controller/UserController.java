package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private  UserMapper userMapper;
    @GetMapping("selectAll")
    public Object selectAll(){
       QueryWrapper<User> wrapper = this.getQueryWrapper();
       return userMapper.selectAll(wrapper);
    }

    private QueryWrapper<User> getQueryWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("name","kwhua");
        return wrapper;
    }

    @GetMapping("selectOrderUser")
    public Object selectOrderUser(@RequestParam("id") Long id){
        return userMapper.selectOrderUser(id);
    }

    @GetMapping("selectUserOrders")
    public Object selectUserOrders(@RequestParam("id") Long id){
        return userMapper.selectUserOrders(id);
    }

    @GetMapping("findUserAndItemsRstMap")
    public Object findUserAndItemsRstMap(@RequestParam("id") Long id){
        return userMapper.findUserAndItemsRstMap(id);
    }
}
