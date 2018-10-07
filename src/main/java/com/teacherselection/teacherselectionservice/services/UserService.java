package com.teacherselection.teacherselectionservice.services;

import com.teacherselection.teacherselectionservice.entities.Role;
import com.teacherselection.teacherselectionservice.entities.Users;
import com.teacherselection.teacherselectionservice.repositories.RoleRepository;
import com.teacherselection.teacherselectionservice.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public Users findUserByEmail(String email) {
    return usersRepository.findByEmail(email);
  }

  public void saveUser(Users user, String role) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    Role userRole = roleRepository.findByRole(role);
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    usersRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Users user = usersRepository.findByEmail(email);
    if(user != null) {
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    } else {
        throw new UsernameNotFoundException("username not found");
    }
  }

  private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
    Set<GrantedAuthority> roles = new HashSet<>();
    userRoles.forEach((role) -> {
        roles.add(new SimpleGrantedAuthority(role.getRole()));
    });

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
    return grantedAuthorities;
  }

  private UserDetails buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
  }
}