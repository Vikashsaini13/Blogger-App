package com.example.BloggerApp.repository;

import com.example.BloggerApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IPostRepo extends JpaRepository<Post,Integer> {
}
