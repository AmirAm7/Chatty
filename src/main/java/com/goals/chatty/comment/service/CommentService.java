package com.goals.chatty.comment.service;

import com.goals.chatty.comment.domain.Comment;
import com.goals.chatty.comment.domain.CommentRepository;
import com.goals.chatty.comment.mapper.CommentMapper;
import com.goals.chatty.comment.rest.response.CommentDto;
import com.goals.chatty.post.domain.Post;
import com.goals.chatty.post.domain.PostRepository;
import com.goals.chatty.post.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final PostServiceImpl postService;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostServiceImpl postService, CommentMapper commentMapper,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Comment> createComment(CommentDto commentDto, long post_id) {
        Post post = postService.findPostById(post_id);
        Comment comment = commentMapper.convertToComment(commentDto);
        if (post != null) {
            comment.setPost(post);
            postService.commentPost(post_id, comment);
        }
        return Optional.of(comment);
    }

    @Override
    public List<CommentDto> getAllComments(long post_id) {
        return commentMapper.convertToListOfCommentDto(commentRepository.getAllByPost_Id(post_id));
    }

    @Override
    public List<CommentDto> getAllCommentsForUser(long user_id) {
        return commentMapper.convertToListOfCommentDto(commentRepository.getAllByUser_Id(user_id));
    }


    @Override
    public void deleteComment(long comment_id) {
        commentRepository.deleteById(comment_id);
    }

    @Override
    public long getCountByPostId(long post_id) {
        return commentRepository.countAllByPost_Id(post_id);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByPostId(long post_id) {
        postRepository.findById(post_id).ifPresent(post -> {
            post.setComments(null);
            postRepository.save(post);
        });
        commentRepository.deleteAllByPost_Id(post_id);
    }
}


