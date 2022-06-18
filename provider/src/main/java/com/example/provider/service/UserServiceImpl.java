package com.example.provider.service;

import com.example.api.model.User;
import com.example.provider.service.impl.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String sayHello(User user) {
        return String.format("user.name = %s,user.age = %s", user.getName(), user.getAge() + "");
    }

}
