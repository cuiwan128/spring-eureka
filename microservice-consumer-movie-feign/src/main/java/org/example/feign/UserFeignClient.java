package org.example.feign;

import org.example.pojo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name="microservice-provider-user")
public interface UserFeignClient {

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);

    @RequestMapping(value = "/getByName1",method = RequestMethod.GET)
    public User getByName1(@RequestParam("id") Long id,@RequestParam("username") String username);

    @RequestMapping(value = "/getByName2",method = RequestMethod.GET)
    public User getByName2(@RequestParam Map<String,Object> map);

    @RequestMapping(value = "/getByName3",method = RequestMethod.POST)
    public User getByName3(@RequestBody User user);

}
