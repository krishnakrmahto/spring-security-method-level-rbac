package io.sampleprojects.spring.rbac.service;

import io.sampleprojects.spring.rbac.dto.AddUserRoleRequest;
import io.sampleprojects.spring.rbac.entity.User;
import io.sampleprojects.spring.rbac.repository.UserRepository;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
public class GroupUserDetailsService implements UserDetailsService {

  private final UserRepository repository;
  private final BCryptPasswordEncoder passwordEncoder;

  private final static String DEFAULT_ROLE_USER = "ROLE_USER";
  private final static String ROLE_ADMIN = "ROLE_ADMIN";
  private final static String ROLE_MODERATOR = "ROLE_MODERATOR";
  private final static List<String> ADMIN_AUTHORIZED_TO_ADD_ROLES = Arrays.asList(ROLE_ADMIN, ROLE_MODERATOR);
  private final static List<String> MODERATOR_AUTHORIZED_TO_ADD_ROLES = Collections.singletonList(ROLE_MODERATOR);


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return repository.findByUsername(username)
        .map(GroupUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

  }

  public void save(User user) {
    user.setRoles(DEFAULT_ROLE_USER);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    repository.save(user);
  }

  public void addUserRoles(AddUserRoleRequest request, String username, Principal principal)
      throws ValidationException {
    List<String> loggedInUserRoles = getLoggedInUserRoles(principal);

    User userToBeUpdated = repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Requested user not found"));

    String newRole = request.getNewRole();
    if (loggedInUserRoles.contains(ROLE_ADMIN) && ADMIN_AUTHORIZED_TO_ADD_ROLES.contains(newRole)) {
      userToBeUpdated.setRoles(userToBeUpdated.getRoles() + "," + newRole);
    } else if (loggedInUserRoles.contains(ROLE_MODERATOR) && MODERATOR_AUTHORIZED_TO_ADD_ROLES.contains(newRole)) {
      userToBeUpdated.setRoles(userToBeUpdated.getRoles() + "," + newRole);
    } else {
      throw new ValidationException("You cannot assign the role to the user.");
    }
  }

  private List<String> getLoggedInUserRoles(Principal principal) {
    User loggedInUser = repository.findByUsername(principal.getName())
        .orElseThrow(() -> new RuntimeException("Invalid logged in user"));

    return Arrays.stream(loggedInUser.getRoles().split(",")).collect(Collectors.toList());
  }
}
