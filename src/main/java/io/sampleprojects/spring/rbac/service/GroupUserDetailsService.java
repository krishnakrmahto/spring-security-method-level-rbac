package io.sampleprojects.spring.rbac.service;

import io.sampleprojects.spring.rbac.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class GroupUserDetailsService implements UserDetailsService {

  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return repository.findByUserName(username)
        .map(GroupUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

  }
}
