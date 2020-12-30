package org.example.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    public IRule ribbonRule(){
        //随机负载
        return new RandomRule();
    }
}
