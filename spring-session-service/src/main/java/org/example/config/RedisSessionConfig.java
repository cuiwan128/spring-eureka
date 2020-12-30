package org.example.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=5, redisNamespace="{spring.session.redis.namespace}")
public class RedisSessionConfig {

}
