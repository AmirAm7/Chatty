package com.goals.chatty.vote.service;

import com.goals.chatty.comment.domain.Comment;
import com.goals.chatty.comment.domain.CommentRepository;
import com.goals.chatty.post.domain.Post;
import com.goals.chatty.post.domain.PostRepository;
import com.goals.chatty.user.domain.User;
import com.goals.chatty.user.service.UserServiceImpl;
import com.goals.chatty.userManagement.config.CurrentUser;
import com.goals.chatty.vote.domain.comment.CommentRating;
import com.goals.chatty.vote.domain.comment.CommentRatingRepository;
import com.goals.chatty.vote.domain.post.PostRating;
import com.goals.chatty.vote.domain.post.PostRatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class VoteService implements IVoteService{

    final PostRatingRepository postRatingRepository;
    final CommentRatingRepository commentRatingRepository;
    final CommentRepository commentRepository;
    final PostRepository postRepository;
    final CurrentUser currentUser;
    final UserServiceImpl userService;

    @Autowired
    public VoteService(PostRatingRepository postRatingRepository, CommentRatingRepository commentRatingRepository, CommentRepository commentRepository, PostRepository postRepository, CurrentUser currentUser, UserServiceImpl userService) {
        this.postRatingRepository = postRatingRepository;
        this.commentRatingRepository = commentRatingRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Long userVotePost(long post_id){
        Post post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userService.getCurrentUser().orElseThrow(()-> new RuntimeException("User not found"));
        List <PostRating> ratings = postRatingRepository.findAll();
        boolean isVoted = ratings.stream()
                .filter(rating -> Objects.equals(rating.getPostRatingId().getUser().getId(),user.getId()))
                .anyMatch(rating -> Objects.equals(rating.getPostRatingId().getPost().getId(), post_id));
        if(isVoted){
            log.info("User {} already voted for post {}", user.getUsername(), post.getId());
            throw  new RuntimeException("User already voted for this post");
        } else if(post != null && user != null){
            try {
                log.info("User {} voted for post {}", user.getUsername(), post.getId());
                postRatingRepository.saveRating(user.getId(), post_id);
            } catch (Exception e){
                log.info(e.getMessage());
            }
        }
        return post_id;
    }


    @Override
    public Long userVoteComment(long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(()-> new RuntimeException("Post not found"));
        User user = userService.getCurrentUser().orElseThrow(()-> new RuntimeException("User not found"));

        List <CommentRating> ratings = commentRatingRepository.findAll();
        boolean isVoted = ratings.stream()
                .filter(rating -> Objects.equals(rating.getRatingIdOfComment().getUser().getId(), user.getId()))
                .anyMatch(rating -> Objects.equals(rating.getRatingIdOfComment().getComment().getId(), comment_id));
        if (isVoted){
            log.info("User {} already voted for comment {}", user.getUsername(), comment.getId());
            throw new RuntimeException("User already voted for this comment");
        } else if (user != null && comment !=null){
            try {
                commentRatingRepository.saveRating(user.getId(), comment_id);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        return comment_id;
    }

    @Override
    public long getCountOfVotesByPostId(long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("Post not found"));
        if(post!= null){
            return postRatingRepository.countByPostId(post_id);
        }
         return 0;
    }

    @Override
    public long getCountOfVotesByCommentId(long comment_id) {
        Post post = postRepository.findById(comment_id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (post != null){
            return postRatingRepository.countByCommentId(comment_id);
        }
        return 0;
    }

    @Override
    public void removeVoteOfPost(long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("Post not found"));
        try {
            if (post != null) {
                List<PostRating> ratings = postRatingRepository.findAll();
                boolean isVoted = ratings.stream()
                        .map(PostRating -> PostRating.getPostRatingId().getUser())
                        .filter(user -> Objects.equals(user.getId(), currentUser.getUserId())).isParallel();

                if (!isVoted) {
                    postRatingRepository.deleteRating(post_id);
                }
            }
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void removeVoteOfComment(long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(()-> new RuntimeException("Post not found"));
        try {
            if (comment!= null){
                List<CommentRating> ratings = commentRatingRepository.findAll();
                boolean isVoted = ratings.stream()
                        .map(CommentRating -> CommentRating.getRatingIdOfComment().getUser())
                        .filter(user ->  Objects.equals(user.getId(), currentUser.getUserId())).isParallel();
                if (!isVoted){
                    commentRatingRepository.deleteRating(comment_id);
                }
            }
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
