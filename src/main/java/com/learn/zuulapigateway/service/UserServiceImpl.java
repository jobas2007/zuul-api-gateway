package com.learn.zuulapigateway.service;

import com.learn.zuulapigateway.domain.User;
import com.learn.zuulapigateway.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IService<User> {

    private final UserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        log.info("UserServiceImpl - findAll() --------------- ");
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public String deleteById(Long id) {
        log.info("UserServiceImpl - deleteById() --------------- ");
        JSONObject jsonObject = new JSONObject();
        try {
            userRepository.deleteById(id);
            jsonObject.put("message", "User Deleted Successfully");
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return jsonObject.toString();
    }
}
