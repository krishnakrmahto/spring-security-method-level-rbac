package io.sampleprojects.spring.rbac.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public String DEFAULT_ROLE_USER = "ROLE_USER";
  public String ROLE_ADMIN = "ROLE_ADMIN";
  public String ROLE_MODERATOR = "ROLE_MODERATOR";
  public List<String> ADMIN_AUTHORIZED_TO_ADD_ROLES = Arrays.asList(ROLE_ADMIN, ROLE_MODERATOR);
  public List<String> MODERATOR_AUTHORIZED_TO_ADD_ROLES = Collections.singletonList(ROLE_MODERATOR);
}
