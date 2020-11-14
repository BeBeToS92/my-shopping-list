package com.bebetos.shoppinglist.services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebetos.shoppinglist.models.Privilege;
import com.bebetos.shoppinglist.models.Role;
import com.bebetos.shoppinglist.repositories.PrivilegeRepository;
import com.bebetos.shoppinglist.repositories.RoleRepository;

@Service
public class RolePrivilegeService {

    @Autowired private RoleRepository roleRepository;
    @Autowired private PrivilegeRepository privilegeRepository;



    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
  
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }


    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
  
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
    
}
