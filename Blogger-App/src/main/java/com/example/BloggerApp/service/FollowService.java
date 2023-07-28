package com.example.BloggerApp.service;

import com.example.BloggerApp.dto.FollowDTO;
import com.example.BloggerApp.model.Follow;
import com.example.BloggerApp.repository.IFollowRepo;
import com.example.BloggerApp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class FollowService {

    @Autowired
    IFollowRepo followRepository;


    @Autowired
    IUserRepo userRepository;
    @Autowired
    UserService userService;

    private List<Follow> allFollows = new ArrayList<>();

    public Follow addFollower(FollowDTO followDTO) {
        Follow follow = new Follow();
        if(followDTO.getFollowingId()==followDTO.getFollowerId()){
            return null;
        }
        Integer followerId = followDTO.getFollowerId();
        Integer followingId = followDTO.getFollowingId();
        follow.setFollowerId(userRepository.findById(followerId).orElse(null));
        follow.setFollowingId(userRepository.findById(followingId).orElse(null));
        allFollows.add(follow);
        return followRepository.save(follow);
    }


    private List<String> followers = new ArrayList<>();
    private List<String> followings = new ArrayList<>();

    public List<String> getFollowers(Integer userId) {
        Set<String> follower=new HashSet<>();
        for (Follow follow : allFollows) {
            if (follow.getFollowingId().getUserId() == userId) {
                follower.add(follow.getFollowerId().getUserName());
            }
        }
        if(follower.size()==0){
            return null;
        }
        return new ArrayList<>(follower);
    }

    public List<String> getFollowings(Integer userId) {
        Set<String> following=new HashSet<>();
        for (Follow follow : allFollows) {
            if (follow.getFollowerId().getUserId() == userId) {
                following.add(follow.getFollowingId().getUserName());
            }
        }
        if(following.size()==0){
            return null;
        }
        return new ArrayList<>(following);
    }


}
