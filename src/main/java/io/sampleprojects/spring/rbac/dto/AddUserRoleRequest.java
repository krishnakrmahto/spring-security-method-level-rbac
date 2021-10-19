package io.sampleprojects.spring.rbac.dto;

import lombok.Data;

@Data
public class AddUserRoleRequest {
  private String newRole;
}
