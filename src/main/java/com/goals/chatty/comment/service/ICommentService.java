package com.goals.chatty.comment.service;

import com.goals.chatty.comment.domain.Comment;
import com.goals.chatty.comment.rest.response.CommentDto;
import com.goals.chatty.post.rest.response.PostDTO;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    Optional<Comment> createComment(CommentDto commentDto, long post_id);
    List<CommentDto> getAllComments(long post_id);
    void deleteComment(long comment_id);
    long getCountByPostId(long post_id);
    void deleteAllCommentsByPostId(long post_id);
    List<CommentDto> getAllCommentsForUser(long post_id);
    //List <CommentDto> giveAllCommentsOfUser(long author_id);

}
