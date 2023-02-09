package com.goals.chatty.comment.controller;

import com.goals.chatty.comment.rest.response.CommentDto;
import com.goals.chatty.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService service) {
        this.commentService = service;
    }

    @PostMapping("/create/{post_id}")
    ResponseEntity <Map<String,String>> createComment(@RequestBody CommentDto commentDTO, @PathVariable long post_id){
        Map<String,String> outputInfo = new HashMap<>();
        outputInfo.put("Description: ", "Comment created successfully");
        commentService.createComment(commentDTO, post_id);
        return new ResponseEntity<>(outputInfo, HttpStatus.CREATED);
    }

    @GetMapping("/giveAll/{post_id}")
    ResponseEntity <List<CommentDto>> giveAllCommentByPostId (@PathVariable(value = "post_id") long post_id){
        List <CommentDto> comments = commentService.getAllComments(post_id);
        return new ResponseEntity<>(comments, HttpStatus.FOUND);
    }

    @GetMapping("/countComments/{post_id}")
    ResponseEntity <Long> countComments (@PathVariable(value = "post_id") long post_id){
        long count = commentService.getCountByPostId(post_id);
        return new ResponseEntity<>(count, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{comment_id}")
    ResponseEntity <Map<String,String>> deleteComment (@PathVariable(value = "comment_id") long comment_id){
        Map<String,String> outputInfo = new HashMap<>();
        outputInfo.put("Description: ", "Comment deleted successfully");
        log.info("Comment  {} deleted successfully", comment_id);
        commentService.deleteComment(comment_id);
        return new ResponseEntity<>(outputInfo, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll/{post_id}")
    ResponseEntity <Map<String,String>> deleteAllComments (@PathVariable(value = "post_id") long post_id){
        Map<String,String> outputInfo = new HashMap<>();
        outputInfo.put("Description: ", "All comments deleted successfully");
        log.info("All comments deleted successfully");
        commentService.deleteAllCommentsByPostId(post_id);
        return new ResponseEntity<>(outputInfo, HttpStatus.OK);
    }

    @GetMapping("/giveAllCommentsForUser/{user_id}")
    ResponseEntity <List<CommentDto>> giveAllCommentsForUser (@PathVariable(value = "user_id") long user_id){
        List <CommentDto> comments = commentService.getAllCommentsForUser(user_id);
        return new ResponseEntity<>(comments, HttpStatus.FOUND);
    }

}
