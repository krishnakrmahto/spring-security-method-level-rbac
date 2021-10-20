package io.sampleprojects.spring.rbac.controller;

import io.sampleprojects.spring.rbac.dto.AddUserRoleRequest;
import io.sampleprojects.spring.rbac.entity.User;
import io.sampleprojects.spring.rbac.service.GroupUserDetailsService;
import java.security.Principal;
import javax.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final GroupUserDetailsService service;

  @PostMapping
  public String joinGroup(@RequestBody User user) {

    service.save(user);

    return "Hi, " + user.getUsername() + "! Welcome to the group.";
  }

  @PutMapping("/{username}/roles/add-role")
  @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
  public String addRole(@PathVariable String username, @RequestBody AddUserRoleRequest addUserRoleRequest, Principal principal) {
    try {
      service.addUserRoles(addUserRoleRequest, username, principal);
      return "Added role to the user";
    } catch (ValidationException e) {
      return "Change role failed: " + e.getMessage();
    }
  }
}
