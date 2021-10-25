package com.learn.zuulapigateway;

import com.learn.zuulapigateway.domain.Role;
import com.learn.zuulapigateway.domain.User;
import com.learn.zuulapigateway.service.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableZuulProxy
@SpringBootApplication
@RequiredArgsConstructor
public class ZuulApiGatewayApplication implements CommandLineRunner {

    private final IService<Role> roleService;

    private final IService<User> userService;

    public static void main(String[] args) {
        SpringApplication.run(ZuulApiGatewayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.saveOrUpdate(new Role(1L, "admin"));
        roleService.saveOrUpdate(new Role(2L, "user"));

        User user1 = new User();
        user1.setEmail("test@user.com");
        user1.setName("Test User");
        user1.setMobile("9787456545");
        user1.setRole(roleService.findById(2L));
        user1.setPassword(new BCryptPasswordEncoder().encode("testuser"));
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setEmail("test@admin.com");
        user2.setName("Test Admin");
        user2.setMobile("9787456545");
        user2.setRole(roleService.findById(1L));
        user2.setPassword(new BCryptPasswordEncoder().encode("testadmin"));
        userService.saveOrUpdate(user2);
    }
}
