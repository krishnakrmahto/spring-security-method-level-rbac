package io.sampleprojects.spring.rbac.service;

import io.sampleprojects.spring.rbac.entity.Post;
import io.sampleprojects.spring.rbac.entity.PostStatus;
import io.sampleprojects.spring.rbac.repository.PostRepository;
import io.sampleprojects.spring.rbac.repository.UserRepository;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public void createPost(Post post, Principal principal) {
    post.setStatus(PostStatus.PENDING);
    post.setUser(userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new RuntimeException("Server error!")));
    postRepository.save(post);
  }
}
