package com.goals.chatty.vote.controller;

import com.goals.chatty.vote.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/vote")
public class VoteController {

    final VoteService voteService;
    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/post/{post_id}")
    public ResponseEntity <Map<String,String>> votePost (@PathVariable(value = "post_id")long post_id) {
        Map<String,String> info = new HashMap<>();
        info.put("Description: ", "Post voted successfully");
        voteService.userVotePost(post_id);
        log.info("Post {} voted successfully", post_id);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PostMapping("/comment/{comment_id}")
    public ResponseEntity <Map<String,String>> voteComment (@PathVariable(value = "comment_id")long comment_id) {
        Map<String,String> info = new HashMap<>();
        info.put("Description: ", "Post voted successfully");
        voteService.userVoteComment(comment_id);
        log.info("Post {} voted successfully", comment_id);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("/countVotesOfPost/{post_id}")
    public ResponseEntity <Long> countOfVotesForPost (@PathVariable (value = "post_id") long post_id) {
        Map<String,String> info = new HashMap<>();
        long countOfVotesOfPost = voteService.getCountOfVotesByPostId(post_id);
        info.put("Description: ", "Count of votes for post " + post_id + " is " + countOfVotesOfPost);
        return new ResponseEntity<>(countOfVotesOfPost, HttpStatus.OK);
    }

    @GetMapping("/countVotesOfComment/{comment_id}")
    ResponseEntity<Long> countOfVotesForComment (@PathVariable(value = "comment_id") long comment_id){
        Map<String,String> info = new HashMap<>();
        long countOfVoteForComment = voteService.getCountOfVotesByCommentId(comment_id);
        info.put("Description: ", "Count of votes for comment " + comment_id + " is " + countOfVoteForComment);
        return  new ResponseEntity<>(countOfVoteForComment, HttpStatus.OK);
    }

    @DeleteMapping("/deleteVoteOfPost/{post_id}")
    public ResponseEntity <Map<String,String>> deleteVoteOfPost (@PathVariable(value = "post_id")long post_id) {
        Map<String,String> info = new HashMap<>();
        info.put("Description: ", "Vote of post deleted successfully");
        voteService.removeVoteOfPost(post_id);
        log.info("Vote of post {} deleted successfully", post_id);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }


    @DeleteMapping("/deleteVoteOfComment/{comment_id}")
    public ResponseEntity <Map<String,String>> deleteVoteOfComment (@PathVariable(value = "comment_id")long comment_id) {
        Map<String,String> info = new HashMap<>();
        info.put("Description: ", "Vote of comment deleted successfully");
        voteService.removeVoteOfComment(comment_id);
        log.info("Vote of comment {} deleted successfully", comment_id);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
}
