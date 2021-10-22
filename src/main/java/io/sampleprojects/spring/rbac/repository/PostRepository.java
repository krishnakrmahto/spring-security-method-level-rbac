package io.sampleprojects.spring.rbac.repository;

import io.sampleprojects.spring.rbac.entity.Post;
import io.sampleprojects.spring.rbac.entity.PostStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

  List<Post> findAllByStatus(PostStatus pending);
}
