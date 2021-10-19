package io.sampleprojects.spring.rbac.service;

import io.sampleprojects.spring.rbac.entity.User;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class GroupUserDetails implements UserDetails {

  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true ;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return user.isActive();
  }
}
