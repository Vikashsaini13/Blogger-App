package com.example.BloggerApp.service;

import com.example.BloggerApp.model.Post;
import com.example.BloggerApp.model.User;
import com.example.BloggerApp.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    IPostRepo postRepo;
    public String createPost(Post post) {
        post.setPostCreatedTimeStamp(LocalDateTime.now());
        postRepo.save(post);
        return "post uploaded!!!!";
    }

    public String removePost(Integer postId, User user) {
        Post post=postRepo.findById(postId).orElse(null);

        if(post!=null && post.getPostOwner().equals(user)) {

            postRepo.deleteById(postId);
            return "Removed successfully!!!";
        }
        else if(post==null){
            return "post to be delete does not exist!!!";
        }

        else{
            return "Un-Authorized deletion....Not Allowed!!!";
        }
    }

//    public List<Post> getAllPostByUserId(Integer userId) {
//        List<Post> posts =postRepo.findAll();
//        List<Post> existing=new ArrayList<>();
//
//        for(Post post :posts){
//            if(post.getPostOwner().getUserId().equals(userId)){
//                existing.add(post);
//            }
//        }
//        return existing;
//    }

    public boolean validatePost(Post blogPost) {
        return blogPost!=null && postRepo.existsById(blogPost.getPostId());
    }


    //----------------------------------------------
    public List<Post> getPostByUserId(Integer userId) {
        List<Post> allPosts=allPosts();
        Set<Post> existingPost=new HashSet<>();
        for(Post post:allPosts){
            if(post.getPostOwner().getUserId()==userId){
                existingPost.add(post);
            }
        }
        if(existingPost.size()==0){
            return null;
        }
        return existingPost.stream().toList();
    }


    public List<Post> allPosts(){
        return  postRepo.findAll();
    }

}
