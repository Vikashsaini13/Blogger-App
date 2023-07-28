package com.example.BloggerApp.controller;

import com.example.BloggerApp.model.Comment;
import com.example.BloggerApp.model.Follow;
import com.example.BloggerApp.model.Post;
import com.example.BloggerApp.model.User;
import com.example.BloggerApp.repository.IUserRepo;
import com.example.BloggerApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    IUserRepo userRepo;

    //get user bby userid
    @GetMapping("user/{id}")
    public User getUserById (@PathVariable Integer id){
        return userService.getUserById(id);
    }

    //get all user
    @GetMapping("users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    //create a user
    @PostMapping("user")
    public String createUser(@RequestBody User user){
        if(userRepo.findByUserHandle(user.getUserHandle())==null){
           return userService.createUser(user);

        }
        else{
            return "user already exist!!...pls choose different userHandle!!!";
        }
    }

    //delete user by user email
    @DeleteMapping("user/{email}/{password}")
    public String deleteUserByEmail(@PathVariable String email,@PathVariable String password){
        User user =userRepo.findFirstByUserEmail(email);
        if(user!=null){
            if(user.getUserPassword().equals(password)){
                userService.deleteUserByEmail(user);
                return "user deletes successfully!!";
            }
            else{
                return "inValid password";
            }
        }
        else{
            return "user with email "+email+ "does not exist!!";
        }
    }

    //update user
    @PutMapping("user/{userId}/{password}")
    public String updateUser(@RequestBody User user,@PathVariable Integer userId,@PathVariable String password){
       Optional<User> existingUser=userRepo.findById(userId);
        if(existingUser.isPresent()){
            if(userId==user.getUserId()){
                if(existingUser.get().getUserPassword().equals(password)){
                    userService.updateUser(user,userId);
                    return "user details for user id "+userId+" updated successfully!!";
                }
                else{
                    return "invalid password!!";
                }
            }
            else{
                return " user id should be same !!!";
            }
        }
        else{
            return "user with user userid "+userId + "does not exist!!";

        }
    }

    //post content in blog

    boolean validUser(String email,String password){
        User user=userRepo.findFirstByUserEmail(email);

       if(user==null){
           return false;
       }

       String userPassword=user.getUserPassword();

       return userPassword.equals(password);
    }

    //get post by user id
//    @GetMapping("post/userId/{userId}")
//    public List<Post> getAllPostByUserId(@PathVariable Integer userId){
//        return userService.getAllPostByUserId(userId);
//    }

    //add post
    @PostMapping("post/{email}/{password}")
    public String createPost(@RequestBody Post post, @PathVariable String email,@PathVariable String password){
        if(validUser(email,password)){
            return userService.createPost(post,email);
        }
        else{
            return "invalid user credentials";
        }
    }


    //deleting a post from blog
    @DeleteMapping("post/{postId}/{email}/{password}")
    public String removePost(@RequestParam Integer postId, @PathVariable String email,@PathVariable String password){
        if(validUser(email,password)){
            return userService.removePost(postId,email);
        }
        else{
            return "not a authenticate user activity!!";
        }
    }

    //add comment on post
    @PostMapping("comment/{commenterEmail}/{password}")
    public String addComment(@RequestBody Comment comment,@PathVariable String commenterEmail,@PathVariable String password){
        if(validUser(commenterEmail,password)){
            return userService.addComment(comment,commenterEmail);
        }
        else{
            return "not a authenticate user activity!!";
        }
    }

    //delete comment
    @DeleteMapping("comment")
    public String removeComment(@RequestParam Integer commentId, @RequestParam String email, @RequestParam String password){
        if(validUser(email,password)){
            return userService.removeComment(commentId,email);
        }
        else{
            return "not a authenticate user activity!!";
        }
    }

    //get all comments on a post
    @GetMapping("comment/{userId}/{postId}")
    public List<String> getAllComments(@PathVariable Integer userId,@PathVariable Integer postId){
        return userService.getAllComments(userId,postId);
    }


    //follow functionality on blog






}
