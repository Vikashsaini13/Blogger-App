package com.example.BloggerApp.controller;

import com.example.BloggerApp.model.Post;
import com.example.BloggerApp.repository.IUserRepo;
import com.example.BloggerApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    IUserRepo userRepository;
    @GetMapping("/getPostsByUserId/{userId}")
    public ResponseEntity<List<Map<String, String>>> getPostsByUserId(@PathVariable Integer userId) {
        if (!userRepository.findById(userId).isPresent()) {
            return new ResponseEntity<>(List.of(Map.of("message", "User with userId " + userId + " doesn't exist")), HttpStatus.NOT_FOUND);
        }
        List<Post> posts = postService.getPostByUserId(userId);
        if (posts == null) {
            return new ResponseEntity<>(List.of(Map.of("message", "User with userId " + userId + " haven't posted any post")), HttpStatus.OK);
        }
        List<Map<String, String>> postDetails = new ArrayList<>();
        for (Post post : posts) {
            Map<String, String> details = new HashMap<>();
            details.put("postId", String.valueOf(post.getPostId()));
            details.put("postContent", post.getPostContent());
            postDetails.add(details);
        }
        return new ResponseEntity<>(postDetails, HttpStatus.OK);
    }
}
