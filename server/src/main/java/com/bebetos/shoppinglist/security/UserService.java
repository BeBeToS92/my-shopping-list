package com.bebetos.shoppinglist.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bebetos.shoppinglist.repositories.RoleRepository;
import com.bebetos.shoppinglist.repositories.UserRepository;
import com.bebetos.shoppinglist.models.Privilege;
import com.bebetos.shoppinglist.models.Role;
import com.bebetos.shoppinglist.models.User;

@Service(value = "userService")
@Transactional
public class UserService implements UserDetailsService {
	
	@Autowired private UserRepository userDao;
    @Autowired private RoleRepository roleDao;
 
    @Override
    public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
  
        Optional<User> optUser = userDao.findByEmail(email);
        User user = optUser.get();
        
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
              " ", " ", true, true, true, true, 
              getAuthorities(Arrays.asList(
                roleDao.findByName("ROLE_USER"))));
        }
        
 
        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), user.isEnabled(), true, true, 
          true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
        Collection<Role> roles) {
    
          return getGrantedAuthorities(getPrivileges(roles));
      }
   
      private List<String> getPrivileges(Collection<Role> roles) {
    
          List<String> privileges = new ArrayList<>();
          List<Privilege> collection = new ArrayList<>();
          for (Role role : roles) {
              collection.addAll(role.getPrivileges());
          }
          for (Privilege item : collection) {
              privileges.add(item.getName());
          }
          return privileges;
      }
   
      private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
          List<GrantedAuthority> authorities = new ArrayList<>();
          for (String privilege : privileges) {
              authorities.add(new SimpleGrantedAuthority(privilege));
          }
          return authorities;
      }
}