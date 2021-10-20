package io.sampleprojects.spring.rbac.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
public class User {

  @Id
  @GeneratedValue
  private int id;

  private String username;

  private String password;

  private boolean active;

  private String roles;

}
