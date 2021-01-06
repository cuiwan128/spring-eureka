package org.example.controller;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;


@RestController
public class UserController {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger("UserController");

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
        LOGGER.info(System.currentTimeMillis()+"--> id:"+id);
        DeferredResult<Object> deferredResult = new DeferredResult<>();
        deferredResult.setResult(userMapper.selectOrderUser(id));
        return deferredResult;
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
