package com.own.redis_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

import com.own.redis_springboot.service.UserService;

import net.bytebuddy.utility.dispatcher.JavaDispatcher.Container;

@SpringBootTest
class RedisSpringbootApplicationTests {

	@Test
	void contextLoads() {
	}

}
