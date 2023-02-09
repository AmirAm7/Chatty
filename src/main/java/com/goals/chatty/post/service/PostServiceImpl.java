package com.goals.chatty.post.service;


import com.goals.chatty.comment.domain.Comment;
import com.goals.chatty.comment.mapper.CommentMapper;
import com.goals.chatty.comment.service.CommentService;
import com.goals.chatty.post.domain.Post;
import com.goals.chatty.post.domain.PostRepository;
import com.goals.chatty.post.mapper.PostMapper;
import com.goals.chatty.post.rest.response.PostDTO;
import com.goals.chatty.user.config.ERole;
import com.goals.chatty.user.domain.User;
import com.goals.chatty.user.service.UserServiceImpl;
import com.goals.chatty.userManagement.config.CurrentUser;
import com.goals.chatty.vote.domain.post.PostRating;
import com.goals.chatty.vote.domain.post.PostRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements IPostService {

    private final PostMapper mapper;
    private final CommentMapper commentMapper;
    private final CurrentUser currentUser;
    private final UserServiceImpl userService;
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final PostRatingRepository postRatingRepository;


    @Autowired
    @Lazy
    public PostServiceImpl(PostMapper postMapper, CommentMapper commentMapper, CurrentUser currentUser,
                           UserServiceImpl userService,
                           CommentService commentService, PostRepository postRepository,
                           PostRatingRepository postRatingRepository) {
        this.mapper = postMapper;
        this.commentMapper = commentMapper;
        this.currentUser = currentUser;
        this.userService = userService;
        this.commentService = commentService;
        this.postRepository = postRepository;
        this.postRatingRepository = postRatingRepository;
    }

    @Override
    public Long createPost(PostDTO postDTO) {
        Post post = postRepository.save(mapper.convertToPost(postDTO));
        return post.getId();
    }

    @Override
    public Post findPostById(long post_id) {
        return postRepository.findById(post_id)
                .orElseThrow(() -> new UsernameNotFoundException("Post not found"));
    }

    @Override
    public Post commentPost(long postId, Comment comment) {
        Post post = postRepository.getById(postId);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        post.setComments(comments);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER')")
    public void deletePostById(long post_id) {
        Post post = findPostById(post_id);
        ERole role = giveCurrentUser().getAuthorities()
                .stream().findFirst().get()
                .getAuthority().equals("ROLE_MODERATOR") ? ERole.ROLE_MODERATOR : ERole.ROLE_USER;
        boolean isModerator = role.equals(ERole.ROLE_MODERATOR);
        boolean isAuthor = currentUser.getUsername().equals(post.getAuthor().getUsername());

        if (isAuthor || isModerator) {
            postRatingRepository.deleteRating(post_id);
            commentService.deleteAllCommentsByPostId(post_id);
            postRepository.deleteById(post_id);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER')")
    public Post updatePost(PostDTO postDto, long post_id) {

        Post post = findPostById(post_id);
        ERole role = giveCurrentUser().getAuthorities()
                .stream().findFirst().get()
                .getAuthority().equals("ROLE_MODERATOR") ? ERole.ROLE_MODERATOR : ERole.ROLE_USER;
        boolean isModerator = role.equals(ERole.ROLE_MODERATOR);
        boolean isAuthor = currentUser.getUsername().equals(post.getAuthor().getUsername());

        if (isAuthor || isModerator) {
            post.setTitle(postDto.getTitle());
            post.setText(postDto.getText());
            return postRepository.save(post);
        }
        return null;
    }

    @Override
    public List<PostDTO> giveAllPostsOfUser(long author_id) {
        List<Post> postsOfUser = postRepository.findAllByAuthor_Id(author_id);
        List<PostDTO> listOfPostDtos = mapper.convertToListOfPostDto(postsOfUser);
        return listOfPostDtos;
    }

    @Override
    @Transactional
    @PreAuthorize("ROLE_MODERATOR")
    public PostDTO mergePosts(long firstPost_id, long secondPost_id, PostDTO postDto) {
    //TODO: mergePosts
        User user = userService.findUserByUsername(currentUser.getUsername());
        Post firstPost = postRepository.getReferenceById(firstPost_id);
        Post secondPost = postRepository.getReferenceById(secondPost_id);

        List<Comment> newPostComments = Stream.concat(firstPost.getComments().stream(), secondPost.getComments().stream())
                .collect(Collectors.toList());

        List<PostRating> newPostRatings = Stream.concat(firstPost.getRatings().stream(), secondPost.getRatings().stream())
                .collect(Collectors.toList());

        ERole role = giveCurrentUser().getAuthorities()
                .stream().findFirst().get()
                .getAuthority().equals("ROLE_MODERATOR") ? ERole.ROLE_MODERATOR : ERole.ROLE_USER;
        boolean isModerator = role.equals(ERole.ROLE_MODERATOR);

        if(!isModerator) {
            throw new UsernameNotFoundException("You are not moderator");
        } else {
            Post newPost = new Post();
            {
                newPost.setAuthor(user);
                newPost.setTitle(postDto.getTitle());
                newPost.setText(postDto.getText());
                newPost.setCreationTime(LocalDateTime.now());
                newPost.setComments(newPostComments);
                newPost.setRatings(newPostRatings);
            }
            postRepository.save(newPost);
            deletePostById(firstPost_id);
            deletePostById(secondPost_id);
            return mapper.convertToPostDTO(newPost);
        }
    }

    public UserDetails giveCurrentUser() {
        return userService.loadUserByUsername(currentUser.getUsername());
    }
}

