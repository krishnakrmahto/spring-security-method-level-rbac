package io.sampleprojects.spring.rbac.service;

import io.sampleprojects.spring.rbac.entity.Post;
import io.sampleprojects.spring.rbac.entity.PostStatus;
import io.sampleprojects.spring.rbac.repository.PostRepository;
import io.sampleprojects.spring.rbac.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Transactional
  public void createPost(Post post, Principal principal) {
    post.setStatus(PostStatus.PENDING);
    post.setUser(userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new RuntimeException("Server error!")));
    postRepository.save(post);
  }

  @Transactional
  public void approvePost(int postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new RuntimeException("Post not found"));

    post.setStatus(PostStatus.APPROVED);
  }

  @Transactional
  public void rejectPost(int postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new RuntimeException("Post not found"));

    post.setStatus(PostStatus.REJECTED);
  }

  @Transactional
  public void approveAllPendingPosts() {
    postRepository.findAllByStatus(PostStatus.PENDING)
    .forEach(pendingPost -> pendingPost.setStatus(PostStatus.APPROVED));
  }

  @Transactional
  public void rejectAllPendingPosts() {
    postRepository.findAllByStatus(PostStatus.PENDING)
    .forEach(pendingPost -> pendingPost.setStatus(PostStatus.REJECTED));
  }

  public List<Post> getAllApprovedPosts() {
    return postRepository.findAllByStatus(PostStatus.APPROVED);
  }
}
