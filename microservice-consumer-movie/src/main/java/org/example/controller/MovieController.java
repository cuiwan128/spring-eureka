package org.example.controller;

import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    @GetMapping("api/movie/{id}")
    public User finByIdUser(@PathVariable Long id){
        String urlRibbon = userServerName+id;
        return this.restTemplate.getForObject(urlRibbon,User.class);
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
}
