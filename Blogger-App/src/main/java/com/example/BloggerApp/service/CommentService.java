package com.example.BloggerApp.service;

import com.example.BloggerApp.model.Comment;
import com.example.BloggerApp.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    ICommentRepo commentRepo;

    public String addComment(Comment comment) {
        comment.setCommentCreationTimeStamp(LocalDateTime.now());
        commentRepo.save(comment);
        return "Comment added!!!";
    }

    public Comment findComment(Integer commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }


    public String removeComment(Comment comment) {
        commentRepo.delete(comment);
        return "comment deleted successfully!!!";
    }

    public List<String> gelAllComment(Integer userId, Integer postId) {
        List<Comment> comments=commentRepo.findAll();

        List<String> postRelatedAllComments= new ArrayList<>();

        for(Comment comment: comments){
            if(comment.getBlogPost().getPostId().equals(postId)){
                postRelatedAllComments.add(comment.getCommentBody());
            }
        }
        if(postRelatedAllComments.size()==0){
            return List.of("post does not have any comment yet!!!");
        }

        return postRelatedAllComments;
    }
}
