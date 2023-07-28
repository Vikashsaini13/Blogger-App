package com.example.BloggerApp.repository;

import com.example.BloggerApp.model.Follow;
import com.example.BloggerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFollowRepo extends JpaRepository<Follow,Integer> {

}
