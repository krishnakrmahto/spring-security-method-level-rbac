package io.sampleprojects.spring.rbac.repository;

import io.sampleprojects.spring.rbac.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {

  Optional<User> findByUserName(String username);
}
