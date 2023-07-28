package com.example.BloggerApp.repository;

import com.example.BloggerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {


    User findByUserHandle(String userHandle);

    User findFirstByUserEmail(String email);
}
