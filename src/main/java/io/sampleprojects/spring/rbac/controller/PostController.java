package io.sampleprojects.spring.rbac.controller;

import io.sampleprojects.spring.rbac.entity.Post;
import io.sampleprojects.spring.rbac.service.PostService;
import java.security.Principal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

  private final PostService service;


  @GetMapping("")
  public List<Post> getAllApprovedPosts() {
    return service.getAllApprovedPosts();
  }

  @PostMapping
  public String createPost(@RequestBody  Post post, Principal principal) {
    service.createPost(post, principal);
    return "Post submitted for approval!";
  }

  @PostMapping("/{postId}/approve")
  @PreAuthorize("hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
  public String approvePost(@PathVariable int postId) {
    service.approvePost(postId);

    return "Post approved";
  }

  @PostMapping("/{postId}/reject")
  @PreAuthorize("hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
  public String rejectPost(@PathVariable int postId, Principal principal) {
    service.rejectPost(postId);

    return "Post rejected";
  }

  @PostMapping("/approve-all-pending")
  @PreAuthorize("hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
  public String approveAllPendingPosts() {
    service.approveAllPendingPosts();

    return "All pending posts approved!";
  }

  @PostMapping("/reject-all-pending")
  @PreAuthorize("hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
  public String rejectAllPendingPosts() {
    service.rejectAllPendingPosts();

    return "All pending posts rejected!";
  }
}
