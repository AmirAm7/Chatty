package com.goals.chatty.post.controller;


import com.goals.chatty.post.domain.Post;
import com.goals.chatty.post.domain.PostRepository;
import com.goals.chatty.post.mapper.PostMapper;
import com.goals.chatty.post.rest.response.PostDTO;
import com.goals.chatty.post.service.IPostService;
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
@RequestMapping(path = "/api/post")
public class PostController {
    private final PostRepository postRepository;

    private final IPostService postService;
    private final PostMapper postMapper;

    @Autowired
    public PostController(IPostService postService, PostMapper postMapper,
                          PostRepository postRepository) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.postRepository = postRepository;
    }

    @PostMapping("/createPost")
    ResponseEntity <Map<String,String>> createPost (@RequestBody PostDTO postDTO) {
        Map<String,String> outputInfo = new HashMap<>();
        Long post_id = postService.createPost(postDTO);
        outputInfo.put("Description: ", "Post created successfully");
        log.info("Post created with id: " + post_id);
        return new ResponseEntity<>(outputInfo, HttpStatus.CREATED);
    }

    @GetMapping("/getPost/{post_id}")
    ResponseEntity <PostDTO> findPostById (@PathVariable(value = "post_id") long post_id){
        PostDTO postDto = postMapper.convertToPostDTO(postService.findPostById(post_id));
        log.info("Post found with id: " + post_id);
        return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }

    @DeleteMapping("/deletePost/{post_id}")
    ResponseEntity<Map<String,String>> deletePostById (@PathVariable(value = "post_id") long post_id){
        Map<String,String> outputInfo = new HashMap<>();
        postService.deletePostById(post_id);
        outputInfo.put("Description: ", "Post deleted successfully");
        log.info("Post deleted with id: " + post_id);
        return new ResponseEntity<>(outputInfo, HttpStatus.OK);
    }

    @PutMapping("/updatePost/{post_id}")
    ResponseEntity<PostDTO> updatePost (@RequestBody PostDTO postDto,
                                        @PathVariable(value = "post_id") long post_id){
        postService.updatePost(postDto, post_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getAllPostsOfUser/{author_id}")
    ResponseEntity <List<PostDTO>> giveAllPostsOfUser (@PathVariable(value = "author_id") long author_id){
        List<PostDTO> postDtos = postService.giveAllPostsOfUser(author_id);
        log.info("Posts found with author id: " + author_id);
        return new ResponseEntity<>(postDtos, HttpStatus.FOUND);
    }


    @PostMapping("/mergePosts/{post_id1}/{post_id2}")
    ResponseEntity <PostDTO> mergePosts(@RequestBody  PostDTO postDTO,
                                   @PathVariable(value = "post_id1") long post_id1,
                                   @PathVariable(value = "post_id2") long post_od2){
        PostDTO newPostDto = postService.mergePosts(post_id1, post_od2, postDTO);
        log.info("Posts merged with id: " + post_id1 + " and " + post_od2);
        return new ResponseEntity<>(newPostDto, HttpStatus.OK);
    }

}
