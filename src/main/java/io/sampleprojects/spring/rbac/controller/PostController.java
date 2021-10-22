package io.sampleprojects.spring.rbac.controller;

import io.sampleprojects.spring.rbac.entity.Post;
import io.sampleprojects.spring.rbac.service.PostService;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

  private final PostService service;

  public String createPost(Post post, Principal principal) {
    service.createPost(post, principal);

    return "Post submitted for approval!";
  }
}
