package org.example.controller;

import org.example.feign.UserFeignClient;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class MovieController {

    @Value("${user.userServerUrl}")
    private String userServerUrl;

    @Value("${userRibbon.userServerName}")
    private String userServerName;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("user/{id}")
    public User findById(@PathVariable Long id){
        //设置restTemplate的HttpRequest工厂
        //restTemplate.setRequestFactory(new Netty4ClientHttpRequestFactory());
        //String url = "http://localhost:8080/"+id;
        String url = userServerUrl+id;
        long s = System.currentTimeMillis();
        User user = this.restTemplate.getForObject(url,User.class);
        long e = System.currentTimeMillis();
        System.out.println(e-s);
        return user;
    }
    @GetMapping("userRibbon/{id}")
    public User finByIdRibbon(@PathVariable Long id){
      String urlRibbon = userServerName+id;
      return this.restTemplate.getForObject(urlRibbon,User.class);
    }
    @GetMapping("loguserintance")
    public Object logUserInstance(){
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
        return  serviceInstance.getServiceId() + "--"+serviceInstance.getHost() + ":"  + serviceInstance.getPort();
    }

    @GetMapping("/feign/user/{id}")
    public User findByIdFeign(@PathVariable long id){
      return this.userFeignClient.findById(id);
    }
    //feign get请求 多参数参数
    @GetMapping("/getByName1")
    public User getByName1(@RequestParam("id") Long id , @RequestParam("username") String username){
      return this.userFeignClient.getByName1(id,username);
    }
    @GetMapping("/getByName2")
    public User getByName2(@RequestParam Map<String,Object> map){
        return this.userFeignClient.getByName2(map);
    }

    @PostMapping("/getByName3")
    public User getByName3(@RequestBody User user){
        return this.userFeignClient.getByName3(user);
    }
}
