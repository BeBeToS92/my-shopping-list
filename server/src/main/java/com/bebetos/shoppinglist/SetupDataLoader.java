package com.bebetos.shoppinglist;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bebetos.shoppinglist.services.RolePrivilegeService;
import com.bebetos.shoppinglist.models.Privilege;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private RolePrivilegeService rolePrivilegeService;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        Privilege basePrivilege = rolePrivilegeService.createPrivilegeIfNotFound("BASE_USER");
        Privilege writePrivilege = rolePrivilegeService.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
  
        List<Privilege> adminPrivileges = Arrays.asList(
          basePrivilege, writePrivilege);    
        rolePrivilegeService.createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        rolePrivilegeService.createRoleIfNotFound("ROLE_USER", Arrays.asList(basePrivilege)); 
    }
 
}