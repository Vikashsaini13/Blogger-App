package com.example.BloggerApp.service;

import com.example.BloggerApp.model.Comment;
import com.example.BloggerApp.model.Follow;
import com.example.BloggerApp.model.Post;
import com.example.BloggerApp.model.User;
import com.example.BloggerApp.repository.IPostRepo;
import com.example.BloggerApp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    IPostRepo postRepo;




    public User getUserById(Integer id) {
        Optional<User> user= userRepo.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        else{
            return null;
        }
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public String createUser(User user) {
        userRepo.save(user);
        return "user created successfully!!!!";
    }

    public void deleteUserByEmail(User user) {
        userRepo.delete(user);
    }


    public void updateUser(User user, Integer userId) {
        Optional<User> existingUser=userRepo.findById(userId);

        existingUser.get().setUserName(user.getUserName());
        existingUser.get().setUserHandle(user.getUserHandle());
        existingUser.get().setAboutUser(user.getAboutUser());
        existingUser.get().setUserEmail(user.getUserEmail());
        existingUser.get().setUserPassword(user.getUserPassword());
        existingUser.get().setGender(user.getGender());


        userRepo.save(existingUser.get());
    }


    public String createPost(Post post, String email) {
        User postOwner=userRepo.findFirstByUserEmail(email);
        post.setPostOwner(postOwner);
        return postService.createPost(post);

    }

    public String removePost(Integer postId, String email) {
        User user=userRepo.findFirstByUserEmail(email);
        return postService.removePost(postId,user);
    }

//    public List<Post> getAllPostByUserId(Integer userId) {
//        if(!userRepo.findById(userId).isPresent()){
//            return List.of(
//        }
//        return postService.getAllPostByUserId(userId);
//    }

    public String addComment(Comment comment, String commenterEmail) {
        User commenter=userRepo.findFirstByUserEmail(commenterEmail);
        comment.setCommenter(commenter);

        boolean validPost=postService.validatePost(comment.getBlogPost());
        if(validPost){
            return commentService.addComment(comment);
        }
        else{
            return "can not comment on a invalid post!!!";
        }

    }

   boolean authorizeCommentRemover(Comment comment,String email){
        String commenterEmail=comment.getCommenter().getUserEmail();
        String postOwnerEmail=comment.getBlogPost().getPostOwner().getUserEmail();

        return commenterEmail.equals(email) || postOwnerEmail.equals(email);
   }

    public String removeComment(Integer commentId, String email) {
        Comment comment=commentService.findComment(commentId);

        if(comment!=null){
            if(authorizeCommentRemover(comment,email)){
                return  commentService.removeComment(comment);
            }
            else{
                return "unauthorized comment deletion occure";
            }
        }
        else{
            return "invalid Comment!!";
        }
    }

    public List<String> getAllComments(Integer userId, Integer postId) {

        if(!userRepo.findById(userId).isPresent()){
            return List.of("user with user id "+userId+"does not exit...pls enter a valid user id");
        }
        if(!postRepo.findById(postId).isPresent()){
            return List.of("post with user id" +postId+"does not exit...pls enter a valid post id");
        }

        if(userRepo.findById(userId).isPresent() && postRepo.findById(postId).isPresent()){
            return commentService.gelAllComment(userId,postId);
        }

        return List.of("pls check your credentials!!");
    }


}


