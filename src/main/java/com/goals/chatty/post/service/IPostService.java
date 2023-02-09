package com.goals.chatty.post.service;

import com.goals.chatty.comment.domain.Comment;
import com.goals.chatty.post.domain.Post;
import com.goals.chatty.post.rest.response.PostDTO;

import java.util.List;

public interface IPostService {
    Long createPost(PostDTO postDTO);
    Post findPostById(long post_id);
    Post commentPost(long postId, Comment comment);
    void deletePostById(long post_id);
    Post updatePost(PostDTO postDto, long post_id);
    List<PostDTO> giveAllPostsOfUser(long author_id);
    PostDTO mergePosts(long firstPost_id, long secondPost_id, PostDTO postDto);
}
