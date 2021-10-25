package com.learn.zuulapigateway.service;

import com.learn.zuulapigateway.domain.Role;
import com.learn.zuulapigateway.domain.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IService<Role> {

    private final RoleRepository roleRepository;

    @Override
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public Role saveOrUpdate(Role role) {
        log.info("RoleServiceImpl - saveOrUpdate-------------");
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public String deleteById(Long id) {
        log.info("RoleServiceImpl - deleteById() --------------- ");
        JSONObject jsonObject = new JSONObject();
        try {
            roleRepository.deleteById(id);
            jsonObject.put("message", "Role Deleted Successfully");
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return jsonObject.toString();
    }
}
