package io.sampleprojects.spring.rbac.repository;

import io.sampleprojects.spring.rbac.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
